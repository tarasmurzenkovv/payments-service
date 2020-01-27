package com.payments.service.dao;

import java.sql.*;

public class ConnectionManager {
    private static final String DB_URL = "jdbc:h2:~/test;;INIT=RUNSCRIPT FROM 'classpath:scripts/schema.sql'";
    private static final String USER = "sa";
    private static final String PASS = "";

    public Connection getConnection(int isolationLevel) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(true);
            connection.setTransactionIsolation(isolationLevel);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
