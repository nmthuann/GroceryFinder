package com.nmt.groceryfinder.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmt.groceryfinder.modules.products.domain.mappers.ProductMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ProductDto;
import com.nmt.groceryfinder.shared.elasticsearch.ElasticsearchService;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;
import com.nmt.groceryfinder.shared.elasticsearch.sync.SyncDataAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/2/2024
 */
@ExtendWith(MockitoExtension.class) // Khởi tạo các mock - junit5
public class SyncDataAspectTest {
    @Mock
    private ElasticsearchService<ProductDocument> elasticsearchService;
    @Mock
    private ProceedingJoinPoint joinPoint;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private SyncDataAspect syncDataAspect;

    private ProductDto productDto;
    private static final String JSON_FILE_PATH = "src/main/resources/data/productDto.json";

    @BeforeEach
    void setUp() throws IOException {
        // MockitoAnnotations.openMocks(this);  // Khởi tạo các mock - junit4
        ObjectMapper objectMapper = new ObjectMapper();
        productDto = objectMapper.readValue(new File(JSON_FILE_PATH), ProductDto.class);
    }

    @Test
    public void shouldSyncDataWhenGivenValidProduct() throws Throwable {
        //ARRANGE
        when(joinPoint.proceed()).thenReturn(Optional.of(productDto));
        ProductDocument productDocument = mock(ProductDocument.class);
        when(productMapper.toDocument(productDto)).thenReturn(productDocument);
        when(elasticsearchService.checkClusterHealth()).thenReturn("green");
        when(elasticsearchService.checkIfIndexExists(anyString())).thenReturn(false);

        ReflectionTestUtils.setField(syncDataAspect, "TEMP_DIRECTORY_NAME", "temp");
        ReflectionTestUtils.setField(syncDataAspect, "CSV_FILE_NAME", "test_products.csv");

        //ACT
        syncDataAspect.syncData(joinPoint, null);

        // ASSERT
        verify(elasticsearchService, times(1)).createIndex(
                eq("products")
        );
        verify(elasticsearchService, times(1)).ingestDocument(
                eq("products"),
                eq("1"),
                eq(productDocument)
        );
    }


    @Test
    public void shouldNotSyncIfProductIsAbsent() throws Throwable {
        // Arrange
        when(joinPoint.proceed()).thenReturn(Optional.empty());

        // Act
        syncDataAspect.syncData(joinPoint, null);

        // Assert
        verify(elasticsearchService, never()).ingestDocument(anyString(), anyString(), any());
    }

    @Test
    public void shouldWriteToFileWhenElasticsearchIsNotReady() throws Throwable {
        // Arrange
        when(joinPoint.proceed()).thenReturn(Optional.of(productDto));
        ProductDocument productDocument = mock(ProductDocument.class);
        when(productMapper.toDocument(productDto)).thenReturn(productDocument);
        when(elasticsearchService.checkClusterHealth()).thenReturn("yellow");

        // Mock static utility methods
        ReflectionTestUtils.setField(syncDataAspect, "TEMP_DIRECTORY_NAME", "temp");
        ReflectionTestUtils.setField(syncDataAspect, "CSV_FILE_NAME", "test_products.csv");

        // Act
        syncDataAspect.syncData(joinPoint, null);

        // Assert
        verify(elasticsearchService, never()).ingestDocument(anyString(), anyString(), any());
        // You would also verify if FileUtil.writeFile() was called,
        // but you might need a custom utility mocking framework like PowerMockito for that.
    }

}
