package com.nmt.groceryfinder.shared.elasticsearch.sync;

import com.nmt.groceryfinder.modules.products.domain.mappers.ProductMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.shared.elasticsearch.ElasticsearchService;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/1/2024
 */
@Aspect
@Component
public class SyncDataAspect {
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
        String indexName = "products";
        ProductDocument productDocument = productMapper.toDocument(product);
        if (!this.elasticsearchService.checkIfIndexExists(indexName)) {
            this.elasticsearchService.createIndex(indexName);
        }
        try {
            this.elasticsearchService.ingestDocument(indexName, product.getId().toString(), productDocument);
        } catch (IOException e) {
            throw new IOException("Failed to sync product with ID: " + product.getId(), e);
        }
    }
}
