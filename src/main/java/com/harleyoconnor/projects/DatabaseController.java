package com.harleyoconnor.projects;

import java.sql.Connection;

/**
 * @author Harley O'Connor
 */
public final class DatabaseController {

    private final Connection connection;

    public DatabaseController(Connection connection) {
        this.connection = connection;
    }

    public <V, T> T select(final String table, final String valueName, final V value, final Class<T> objectType) {
        final String query = "select * from `" + DatabaseConstants.SCHEMA + "`." + table + " where " + valueName + " like " + value;
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

}
