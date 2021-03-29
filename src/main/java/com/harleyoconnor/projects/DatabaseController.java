package com.harleyoconnor.projects;

import com.harleyoconnor.javautilities.pair.Pair;
import com.harleyoconnor.projects.serialisation.exceptions.NoSuchRowException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        final List<Object> args = Stream.of(valuesToUpdate).map(Pair::getValue).collect(Collectors.toList());
        args.add(primaryFieldValue);

        this.executePreparedStatement(statementBuilder.toString(), args);
    }

    public void insertUnsafe(final String table, final Pair<String, Object>... valuesToInsert) {
        try {
            this.insert(table, valuesToInsert);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(final String table, final Pair<String, Object>... valuesToInsert) throws SQLException {
        final var statementBuilder = new StringBuilder("insert into " + table + " (");

        for (int i = 0; i < valuesToInsert.length; i++) {
            statementBuilder.append(valuesToInsert[i].getKey()).append(i != valuesToInsert.length - 1 ? ", " : " ");
        }

        statementBuilder.append(") values (");

        for (int i = 0; i < valuesToInsert.length; i++) {
            statementBuilder.append("?").append(i != valuesToInsert.length - 1 ? ", " : " ");
        }

        this.executePreparedStatement(statementBuilder.append(")").toString(), Stream.of(valuesToInsert).map(Pair::getValue).collect(Collectors.toUnmodifiableList()));
    }

    private void executePreparedStatement(final String sqlQuery, final List<Object> args) throws SQLException {
        final var statement = this.connection.prepareStatement(sqlQuery);

        for (int i = 1; i <= args.size(); i++) {
            statement.setObject(i, args.get(i - 1));
        }

        statement.executeQuery();
    }

    public int getMaxOrDefault(final String table, final String fieldName, final int defaultValue) {
        try {
            final int max = this.getMax(table, fieldName);
            return max == -1 ? defaultValue : max;
        } catch (final SQLException e) {
            return defaultValue;
        }
    }

    public int getMaxUnsafe(final String table, final String fieldName) {
        try {
            return this.getMax(table, fieldName);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMax(final String table, final String fieldName) throws SQLException {
        final var resultSet = this.connection.prepareStatement("select max(" + fieldName + ") from " + table).executeQuery();

        if (!resultSet.next())
            return -1;

        return resultSet.getInt(1);
    }

    public boolean valueExists(final String table, final String fieldName, final Object fieldValue) {
        try {
            // TODO: Make a method that doesn't involve pointlessly transferring this data to the client.
            this.select(table, fieldName, fieldValue);
        } catch (final NoSuchRowException e) {
            return false;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Connection getConnection() {
        return connection;
    }

}
