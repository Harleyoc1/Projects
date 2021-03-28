package com.harleyoconnor.projects;

import com.harleyoconnor.javautilities.pair.Pair;
import com.harleyoconnor.projects.serialisation.exceptions.NoSuchRowException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Harley O'Connor
 */
public final class DatabaseController {

    private final Connection connection;

    public DatabaseController(Connection connection) {
        this.connection = connection;
    }

    public ResultSet selectUnsafe(final String table, final String valueName, final Object value) {
        try {
            return this.select(table, valueName, value);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet select(final String table, final String valueName, final Object value) throws SQLException {
        final var statement = this.connection.prepareStatement(
                "select * from `" + DatabaseConstants.SCHEMA + "`." + table + " where " + valueName + " = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        statement.setObject(1, value);

        final var resultSet = statement.executeQuery();

        if (!resultSet.next())
            throw new NoSuchRowException("No row could be found where '" + valueName + "' is '" + value + "'.");

        return resultSet;
    }

    public void updateUnsafe(final String table, final String primaryFieldName, final Object primaryFieldValue, final Pair<String, Object>... valuesToUpdate) {
        try {
            this.update(table, primaryFieldName, primaryFieldValue, valuesToUpdate);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(final String table, final String primaryFieldName, final Object primaryFieldValue, final Pair<String, Object>... valuesToUpdate) throws SQLException {
        final var statementBuilder = new StringBuilder("update " + table + " set ");

        for (int i = 0; i < valuesToUpdate.length; i++) {
            statementBuilder.append(valuesToUpdate[i].getKey()).append(" = ?").append(i != valuesToUpdate.length - 1 ? ", " : " ");
        }

        statementBuilder.append("where ").append(primaryFieldName).append( " = ?;");

        final var statement = this.connection.prepareStatement(statementBuilder.toString());

        int i;
        for (i = 1; i <= valuesToUpdate.length; i++) {
            statement.setObject(i, valuesToUpdate[i - 1].getValue());
        }
        statement.setObject(i, primaryFieldValue);

        statement.executeQuery();
    }

    public Connection getConnection() {
        return connection;
    }

}
