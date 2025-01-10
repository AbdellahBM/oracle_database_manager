package com.ma.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/test-db")
    public String testDatabaseConnection() {
        StringBuilder result = new StringBuilder();

        try (Connection connection = dataSource.getConnection()) {
            // Test simple : récupérer les tables de l'utilisateur
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables");

            result.append("Tables in the database: <br>");
            while (rs.next()) {
                result.append(rs.getString("table_name")).append("<br>");
            }
        } catch (Exception e) {
            return "Error while connecting to the database: " + e.getMessage();
        }

        return result.toString();
    }
}
