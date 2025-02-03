package com.wgapp.worksheetgenerator.Config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;


    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        DB_URL = dotenv.get("DB_URL");
        DB_USER = dotenv.get("DB_USER");
        DB_PASSWORD = dotenv.get("DB_PASSWORD");

    }

    // Method to establish a connection to the database
// Method to establish a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

}
