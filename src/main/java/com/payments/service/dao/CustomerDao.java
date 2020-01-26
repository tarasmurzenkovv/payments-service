package com.payments.service.dao;

import com.payments.service.model.Customer;

import javax.inject.Inject;
import java.sql.*;
import java.util.Optional;

public class CustomerDao {
    private final ConnectionManager connectionManager;

    @Inject
    public CustomerDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int createCustomer(String customerName) {
        var customerId = 0;
        var createCustomerSql = "INSERT INTO customer (name) VALUES(?)";
        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(createCustomerSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customerName);
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                try (var resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        customerId = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerId;
    }

    public Optional<Customer> findCustomerById(int customerId) {
        final var createCustomerSql = "SELECT id, name from customer WHERE customer.id = ? ";
        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, customerId);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    var customerName = resultSet.getString("name");
                    return Optional.of(Customer.of(customerId, customerName));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int deleteById(int id) {
        final var createCustomerSql = "DELETE FROM customer WHERE customer.id = ? ";
        try (var connection = connectionManager.getConnection();
             var statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
