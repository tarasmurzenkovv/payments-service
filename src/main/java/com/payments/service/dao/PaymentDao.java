package com.payments.service.dao;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.*;

@Slf4j
public class PaymentDao {
    private final ConnectionManager connectionManager;

    @Inject
    public PaymentDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void transfer(int accountIdFrom, int accountIdTo, BigDecimal amount) {
        try (Connection connection = connectionManager.getConnection();
             ResultSet lockResultSet = PaymentDao.makeLockPreparedStatement(connection, accountIdTo);
             ResultSet lockResultSetCredit = PaymentDao.makeLockPreparedStatement(connection, accountIdFrom)) {
            debitAmount(amount, lockResultSet);
            creditAccount(amount, lockResultSetCredit);
        } catch (SQLException e) {
            log.error("Got error during payment. Exception message is '{}'", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static void debitAmount(BigDecimal amount, ResultSet lockResultSet) throws SQLException {
        lockResultSet.next();
        BigDecimal bigDecimal = lockResultSet.getBigDecimal(2);
        lockResultSet.updateBigDecimal(2, bigDecimal.subtract(amount));
        lockResultSet.updateRow();
    }

    private static void creditAccount(BigDecimal amount, ResultSet lockResultSet) throws SQLException {
        if (lockResultSet.next()) {
            BigDecimal bigDecimal = lockResultSet.getBigDecimal(2);
            lockResultSet.updateBigDecimal(2, bigDecimal.add(amount));
            lockResultSet.updateRow();
        }
    }

    @SneakyThrows
    private static ResultSet makeLockPreparedStatement(Connection connection, int accountId) {
        String lockAccount = "SELECT id, amount FROM payments.account WHERE id = %d FOR UPDATE ";
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        return statement.executeQuery(String.format(lockAccount, accountId));
    }
}
