package io.github.bragabriel.timepunch_api.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnectionAndQuery() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            Assertions.assertNotNull(connection, "The database connection should be valid!");
            Assertions.assertTrue(connection.isValid(2), "The connection should be active and valid.");

            ResultSet resultSet = statement.executeQuery("SELECT 1");
            Assertions.assertTrue(resultSet.next(), "The query result should be valid!");
            Assertions.assertEquals(1, resultSet.getInt(1), "The expected value of the query is 1.");
        }
    }

}
