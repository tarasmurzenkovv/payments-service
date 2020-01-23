package com.payments.service.dao;

import java.sql.*;

public class DbDao {
    private static final String DB_URL = "jdbc:h2:~/test;;INIT=RUNSCRIPT FROM 'classpath:scripts/schema.sql'";
    private static final String USER = "sa";
    private static final String PASS = "";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}