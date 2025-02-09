package com.wgapp.worksheetgenerator.Config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;


    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        URL = dotenv.get("SQLSERVER_URL_DB");
        USER = dotenv.get("SQLSERVER_USER");
        PASSWORD = dotenv.get("SQLSERVER_PASSWORD");


    }

    // Method to establish a connection to the database
// Method to establish a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
