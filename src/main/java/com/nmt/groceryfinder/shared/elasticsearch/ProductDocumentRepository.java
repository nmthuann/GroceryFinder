package com.nmt.groceryfinder.shared.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, String> {
}
