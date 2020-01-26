package com.payments.service.dao;

import lombok.SneakyThrows;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.*;

public class PaymentDao {
    private final ConnectionManager connectionManager;

    @Inject
    public PaymentDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void transfer(int accountIdFrom, int accountIdTo, BigDecimal amount) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement lockAccountsStatement = PaymentDao.makeLockPreparedStatement(connection, accountIdTo, accountIdFrom);
             PreparedStatement debitStatement = PaymentDao.debitAccount(connection, accountIdTo, amount);
             PreparedStatement creditStatement = PaymentDao.creditAccount(connection, accountIdTo, amount)) {
            connection.setAutoCommit(true);
            //connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //lockAccountsStatement.execute();
            debitStatement.executeUpdate();
            creditStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static PreparedStatement makeLockPreparedStatement(Connection connection, int firstAccountId, int secondAccountId) {
        String lockAccounts = "SELECT id, amount FROM account WHERE id in (?, ?) FOR UPDATE ";
        PreparedStatement lockAccountsStatement = connection.prepareStatement(lockAccounts);
        lockAccountsStatement.setInt(1, firstAccountId);
        lockAccountsStatement.setInt(2, secondAccountId);
        return lockAccountsStatement;
    }

    @SneakyThrows
    private static PreparedStatement debitAccount(Connection connection, int accountId, BigDecimal amount) {
        String debitAccount = "UPDATE account SET amount = amount + (?) WHERE id = (?)";
        PreparedStatement debitStatement = connection.prepareStatement(debitAccount);
        debitStatement.setBigDecimal(1, amount);
        debitStatement.setInt(2, accountId);
        return debitStatement;
    }

    @SneakyThrows
    private static PreparedStatement creditAccount(Connection connection, int accountId, BigDecimal amount) {
        String creditAccount = "UPDATE account SET amount = ? WHERE id = ?";
        PreparedStatement creditStatement = connection.prepareStatement(creditAccount);
        creditStatement.setBigDecimal(1, amount);
        creditStatement.setInt(2, accountId);
        return creditStatement;
    }
}
