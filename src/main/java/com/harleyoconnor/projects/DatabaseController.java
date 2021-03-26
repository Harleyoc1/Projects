package com.harleyoconnor.projects;

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

    public ResultSet select(final String table, final String valueName, final Object value) {
        try {
            final ResultSet resultSet = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("select * from `" + DatabaseConstants.SCHEMA + "`." + table + " where " + valueName + " like '" + value + "'");
            resultSet.next();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
