package com.nmt.groceryfinder.shared.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;


import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/1/2024
 */
@Service
public class ElasticsearchService<T> implements IElasticsearchService <T> {
    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ElasticsearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }


    @Override
    public String createIndex(String indexName) throws IOException {
        return elasticsearchClient.indices().create(c -> c.index(indexName)).index();
    }



    @Override
    public Result ingestDocument(String indexName, String id, T document) throws IOException {
        IndexRequest<T> indexRequest = IndexRequest.of(
                request -> request
                        .index(indexName)
                        .id(id)
                        .document(document)
        );
        return elasticsearchClient.index(indexRequest).result();
    }

    @Override
    public T getDocumentById(String indexName, String id) throws IOException {
        GetResponse<T> response = elasticsearchClient.get(
                g -> g.index(indexName).id(id),
                (Class<T>) Object.class
        );
        return response.found() ? response.source() : null;
    }

    @Override
    public List<T> searchDocuments(String indexName, String fieldName, String text) throws IOException {
        List<T> responseList = new ArrayList<>();
        SearchResponse<T> searchResponse = elasticsearchClient.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .fuzzy(f -> f
                                        .field(fieldName)
                                        .value(text)
                                        .fuzziness("AUTO")
                                )),
                (Class<T>) Object.class); // Giả định kiểu Object, bạn có thể sử dụng kiểu cụ thể hơn
        for (Hit<T> hit : searchResponse.hits().hits()) {
            responseList.add(hit.source());
        }
        return responseList;
    }

    @Override
    public Boolean checkIfIndexExists(String indexName) throws IOException {
        ExistsRequest request = ExistsRequest.of(b -> b.index(indexName));
        return elasticsearchClient.indices().exists(request).value();
    }

    @Override
    public String checkClusterHealth() {
        try {
            HealthResponse healthResponse = this.elasticsearchClient.cluster().health();
            return healthResponse.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Elasticsearch is not available. Error: " + e.getMessage();
        }
    }
}




//    public String createIndex(String indexName) throws IOException {
//        return elasticsearchClient.indices().create(c -> c.index(indexName)).index();
//    }
//
//    public Result ingestDocument(String indexName, String id, ProductDocument product) throws IOException {
//        IndexRequest<Object> indexRequest = IndexRequest.of(
//                request -> request
//                        .index(indexName)
//                        .id(id)
//                        .document(product)
//        );
//        return elasticsearchClient.index(indexRequest).result();
//    }
//
//    public Object getProductDocumentById(String indexName, String id) throws IOException {
//        GetResponse<ProductDocument> response = elasticsearchClient.get(
//                g -> g.index(indexName).id(id),
//                ProductDocument.class
//        );
//        if (response.found()) {
//            return response.source();
//        } else {
//            return "Product Not Found";
//        }
//    }
//
//    public List<ProductDocument> searchDocuments(
//            String indexName,
//            String fieldName,
//            String text
//    ) throws IOException {
//        List<ProductDocument> responseList = new ArrayList<>();
//        SearchResponse<ProductDocument> searchResponse = elasticsearchClient.search(
//                s -> s
//                        .index(indexName)
//                        .query(q -> q
//                                .fuzzy(f -> f
//                                                .field(fieldName)     // Trường để tìm kiếm
//                                                .value(text)          // Giá trị fuzzy
//                                                .fuzziness("AUTO")
//                                        // Mức độ cho phép của sự khác biệt (AUTO điều chỉnh tự động)
//                                )),
//                ProductDocument.class);
//        for (Hit<ProductDocument> hit : searchResponse.hits().hits()) {
//            responseList.add(hit.source());
//        }
//        return responseList;
//    }
