package com.nmt.groceryfinder.shared.elasticsearch;

import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;

import java.io.IOException;
import java.util.List;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/1/2024
 */
public interface IElasticsearchService<T> {
    String createIndex(String indexName) throws IOException;
    Result ingestDocument(
            String indexName,
            String id,
            T document
    ) throws IOException;
    Object getDocumentById(String indexName, String id) throws IOException;
    List<T> searchDocuments(
            String indexName,
            String fieldName,
            String text
    ) throws IOException;
    Boolean checkIfIndexExists(String indexName) throws IOException;
    String checkClusterHealth();
}
