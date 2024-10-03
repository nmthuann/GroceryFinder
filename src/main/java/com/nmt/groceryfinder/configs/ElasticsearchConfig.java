package com.nmt.groceryfinder.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Value("${spring.elasticsearch.endpoint}")
    private String endpoint;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;
    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            if (endpoint.contains("localhost")) {
                return ClientConfiguration.builder()
                        .connectedTo("localhost:9200")
                        .build();
            }
            URI uri = new URI(endpoint);
            String scheme = uri.getScheme();
            String hostname = uri.getHost();
            int port = uri.getPort() == -1 ? 443 : uri.getPort();
            if ("https".equalsIgnoreCase(scheme)) {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);

                return ClientConfiguration.builder()
                        .connectedTo(inetSocketAddress)
                        .usingSsl()
                        .withBasicAuth(username, password)
                        .build();
            } else {
                throw new RuntimeException("Only HTTPS endpoints are supported except localhost");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid Elasticsearch endpoint: " + endpoint, e);
        }
    }
}
