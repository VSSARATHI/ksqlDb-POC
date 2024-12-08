package org.example.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MySQLConnector {
    public static void main(String[] args) {
        // JDBC URL, username, and password for MySQL server
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";  // Replace with your database details
        String username = "user";  // Your MySQL username
        String password = "password";  // Your MySQL password

        // SQL query to fetch all records from the 'users' table
        String query = "SELECT * FROM users";

        // Establishing connection and querying the database
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Process the result set
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");  // Replace with your actual column names
                String userName = resultSet.getString("name");
                String email = resultSet.getString("email");

                // Print the records
                System.out.println("User ID: " + userId + ", Name: " + userName + ", Email: " + email);
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database or querying the table.");
            e.printStackTrace();
        }
    }
}

