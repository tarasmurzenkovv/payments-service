package com.payments.service.dao;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.*;

import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

@Slf4j
public class PaymentDao {
    private final ConnectionManager connectionManager;

    @Inject
    public PaymentDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void transfer(int accountIdFrom, int accountIdTo, BigDecimal amount) {
        try (var connection = connectionManager.getConnection(Connection.TRANSACTION_SERIALIZABLE);
             var lockResultSet = makeLockPreparedStatement(connection, accountIdTo);
             var lockResultSetCredit = makeLockPreparedStatement(connection, accountIdFrom)) {
            debitAmount(amount, lockResultSet);
            creditAccount(amount, lockResultSetCredit);
        } catch (SQLException e) {
            log.error("Got error during payment. Exception message is '{}'", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static void debitAmount(BigDecimal amount, ResultSet lockResultSet) {
        if (lockResultSet.next()) {
            BigDecimal bigDecimal = lockResultSet.getBigDecimal(2);
            lockResultSet.updateBigDecimal(2, bigDecimal.subtract(amount));
            lockResultSet.updateRow();
        }
    }

    @SneakyThrows
    private static void creditAccount(BigDecimal amount, ResultSet lockResultSet) {
        if (lockResultSet.next()) {
            BigDecimal bigDecimal = lockResultSet.getBigDecimal(2);
            lockResultSet.updateBigDecimal(2, bigDecimal.add(amount));
            lockResultSet.updateRow();
        }
    }

    @SneakyThrows
    private static ResultSet makeLockPreparedStatement(Connection connection, int accountId) {
        var lockAccount = "SELECT id, amount FROM payments.account WHERE id = ? FOR UPDATE ";
        var statement = connection.prepareStatement(lockAccount, TYPE_SCROLL_SENSITIVE, CONCUR_UPDATABLE);
        statement.setInt(1, accountId);
        return statement.executeQuery();
    }
}
