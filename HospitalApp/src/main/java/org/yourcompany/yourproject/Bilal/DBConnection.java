package org.yourcompany.yourproject.Bilal;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static void main(String[] args) {
        // JDBC URL with connection details
        String url = "jdbc:postgresql://localhost:5432/hospital"; // URL for PostgreSQL server
        String user = "hospitaladmin"; // User for authentication
        String password = "hospitaladmin"; // Password for the user            
        try {
            // Establish the connection
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database!");
            connection.close();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
    }
}
