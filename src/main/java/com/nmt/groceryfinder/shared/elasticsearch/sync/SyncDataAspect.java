package com.nmt.groceryfinder.shared.elasticsearch.sync;

import com.nmt.groceryfinder.modules.products.domain.mappers.ProductMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.shared.elasticsearch.ElasticsearchService;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;
import com.nmt.groceryfinder.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/1/2024
 */
@Aspect
@Slf4j
@Component
public class SyncDataAspect {
    private static final String TEMP_DIRECTORY_NAME = "temp";
    private static final String CSV_FILE_NAME = "temp_products.csv";
    private static final String CSV_HEADER =
            "id,name,slug,productLine,productSpecs,description,status,isDeleted,prioritySort,brandName,categoryName,supplierName";
    private static final String INDEX_NAME = "products";

    private final ElasticsearchService<ProductDocument> elasticsearchService;
    private final ProductMapper productMapper;

    @Autowired
    public SyncDataAspect(
            ElasticsearchService<ProductDocument> elasticsearchService,
            ProductMapper productMapper
    ) {
        this.elasticsearchService = elasticsearchService;
        this.productMapper = productMapper;
    }

    @Around("@annotation(syncData)")
    public Object syncData(ProceedingJoinPoint joinPoint, SyncData syncData) throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof Optional<?> optional && optional.isPresent()) {
            ProductDto productDto = (ProductDto) optional.get();
            new Thread(() -> {
                try {
                    syncProductToElasticsearch(productDto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();        }
        return result;
    }

    private void syncProductToElasticsearch(ProductDto product) throws IOException {
        String indexName = INDEX_NAME;
        ProductDocument productDocument = productMapper.toDocument(product);
        if (!isElasticsearchReady()) {
            log.warn("Elasticsearch is not ready. Syncing data to file.");
            writeToTempFile(productDocument);
            return;
        }
        if (!this.elasticsearchService.checkIfIndexExists(indexName)) {
            this.elasticsearchService.createIndex(indexName);
        }
        try {
            this.elasticsearchService.ingestDocument(indexName, product.getId().toString(), productDocument);
        } catch (IOException e) {
            throw new IOException("Failed to sync product with ID: " + product.getId(), e);
        }
    }

    private boolean isElasticsearchReady() {
        return this.elasticsearchService.checkClusterHealth().contains("green");
    }

    private void writeToTempFile(ProductDocument productDocument) {
        String filePath = getTempFilePath();
        FileUtil.createCsvFileWithHeader(filePath, CSV_HEADER);
        String csvLine = createCsvLine(productDocument);
        FileUtil.writeFile(filePath, csvLine, true);
        log.info("Product data synced to temporary CSV file: {}", productDocument);
    }

    private String getTempFilePath() {
        String projectDir = Paths.get("").toAbsolutePath().toString();
        String tempDir = TEMP_DIRECTORY_NAME;
        FileUtil.createDirectory(projectDir, tempDir);
        return Paths.get(projectDir, tempDir, CSV_FILE_NAME).toString();
    }


    private String createCsvLine(ProductDocument productDocument) {
        return String.format("%s,%s,%s,%s,%s,%b,%b,%d,%s,%s,%s",
                productDocument.id(),
                productDocument.productName(),
                productDocument.slug(),
                productDocument.productLine(),
                productDocument.description(),
                productDocument.status(),
                productDocument.isDeleted(),
                productDocument.prioritySort(),
                productDocument.brandName(),
                productDocument.categoryName(),
                productDocument.supplierName());
    }
}
