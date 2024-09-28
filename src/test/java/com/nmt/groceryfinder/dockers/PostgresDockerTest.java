package com.nmt.groceryfinder.dockers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/28/2024
 */
@Testcontainers
public class PostgresDockerTest {
    @Container
    public PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("tester")
            .withPassword("123456");

    @Test
    void testPostgresRunning() throws Exception {
        // Arrange-Act-Assert
        assert (postgresContainer.isRunning());

        // connect
        try (Connection connection = DriverManager.getConnection(
            postgresContainer.getJdbcUrl(),
            postgresContainer.getUsername(),
            postgresContainer.getPassword()
        )){
            try(Statement statement = connection.createStatement()){
                statement.execute("CREATE TABLE test(id INT PRIMARY KEY, name VARCHAR(255));");
                statement.execute("INSERT INTO test(id, name) VALUES (1, 'test-name');");

                ResultSet resultSet = statement.executeQuery("SELECT name FROM test WHERE id = 1;");
                resultSet.next();
                String name = resultSet.getString("name");

                assertEquals("test-name", name);
            }
        }

    }
}
