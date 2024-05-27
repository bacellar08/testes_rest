package br.com.apirest.integrationtests.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

public class TestContainerTest {
    @Test
    void testMySQLContainer() {
        try (MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.4")) {
            mysql.start();
            System.out.println("MySQL container started with URL: " + mysql.getJdbcUrl());
            System.out.println("Username: " + mysql.getUsername());
            System.out.println("Password: " + mysql.getPassword());
        }
    }
}
