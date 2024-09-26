package com.nmt.groceryfinder.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
@Configuration
@EnableElasticsearchRepositories(
        basePackages = "org.springframework.data.elasticsearch.repository"
)
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}
