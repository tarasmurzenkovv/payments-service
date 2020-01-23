package com.payments.service.dao;

import com.payments.service.model.Customer;

import javax.inject.Inject;
import java.sql.*;
import java.util.Optional;

public class CustomerDao {
    private final DbDao dbDao;

    @Inject
    public CustomerDao(DbDao dbDao) {
        this.dbDao = dbDao;
    }

    public int createCustomer(String customerName) {
        int customerId = 0;
        String createCustomerSql = "INSERT INTO customer (name) VALUES(?)";
        try (Connection connection = dbDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customerName);
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
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
        final String createCustomerSql = "SELECT id, name from customer WHERE customer.id = ? ";
        try (Connection connection = dbDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    return Optional.of(Customer.of(customerId, customerName));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int deleteById(int id) {
        final String createCustomerSql = "DELETE FROM customer WHERE customer.id = ? ";
        try (Connection connection = dbDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
