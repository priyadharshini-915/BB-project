package com.bloodbank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Reusable database connection utility.
 * Centralizes the JDBC connection logic so it is not repeated
 * across DAO classes. Only the config below needs to be changed.
 */
public class DBConnection {

    // ---------- DATABASE CONFIGURATION (edit these) ----------
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blood_bank_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "priyadharshini@915";
    // ----------------------------------------------------------

    private static Connection connection = null;

    /**
     * Returns a single shared database connection.
     * Loads the MySQL driver once and opens the connection.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return connection;
    }
}
