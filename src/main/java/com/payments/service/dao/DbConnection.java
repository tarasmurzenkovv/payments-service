package com.payments.service.dao;

import java.sql.*;
import java.util.function.Function;

public class DbConnection {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    public <T> T select(String query, Function<ResultSet, T> mapper) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            return mapper.apply(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
