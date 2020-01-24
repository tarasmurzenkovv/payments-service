package com.payments.service.dao;

import com.payments.service.model.Account;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class AccountDao {
    private final ConnectionManager connectionManager;

    @Inject
    public AccountDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int create(int customerId, BigDecimal amount) {
        int accountId = 0;
        String createCustomerSql = "INSERT INTO account (customer, amount) VALUES(?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, customerId);
            statement.setBigDecimal(1, amount);
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accountId;
    }

    public Optional<Account> findById(int id) {
        final String createCustomerSql = "SELECT id, customer, amount from account WHERE account.id = ? ";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int customerId = resultSet.getInt("customer");
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    return Optional.of(Account.of(id, customerId, amount));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int deleteById(int id) {
        final String createCustomerSql = "DELETE FROM account WHERE account.id = ? ";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(createCustomerSql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
