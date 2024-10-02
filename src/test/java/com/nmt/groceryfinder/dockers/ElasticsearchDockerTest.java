package com.nmt.groceryfinder.dockers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/28/2024
 */
@Testcontainers
public class ElasticsearchDockerTest {
    @Container
    private static final ElasticsearchContainer elasticsearchContainer =
            new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.17.24")
            .withExposedPorts(9200, 9300);

    @BeforeAll
    public static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        elasticsearchContainer.stop();
    }

    @Test
    public void shouldStartElasticsearchContainer() throws IOException {
        assertThat(elasticsearchContainer.isRunning()).isTrue();

        String httpHostAddress = elasticsearchContainer.getHttpHostAddress();
        System.out.println("Elasticsearch HTTP URL: " + httpHostAddress);

        String clusterHealthUrl = "http://" + httpHostAddress + "/_cluster/health";
        String healthResponse = fetchUrl(clusterHealthUrl);

        System.out.println("Cluster Health Response: " + healthResponse);
        assertThat(healthResponse).contains("\"status\":\"green\"");
    }

    private String fetchUrl(String url) throws IOException {
        URI uri = URI.create(url); // Create URI first
        URL urlObj = uri.toURL();  // Convert URI to URL
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        }
    }

}
