package com.nmt.groceryfinder.shared.health;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/1/2024
 */
@Component
public class ElasticsearchHealthCheck {
    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ElasticsearchHealthCheck(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

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
