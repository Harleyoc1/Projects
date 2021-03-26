package com.harleyoconnor.projects.serialisation.util;

import com.harleyoconnor.projects.serialisation.exceptions.NoSuchColumnException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class ResultSetConversions {

    @FunctionalInterface
    private interface Converter<R> {
        R convert(final ResultSet resultSet, final String column) throws SQLException;
    }

    public static final class ConverterRegistry {
        private final Map<Class<?>, ResultSetConversions.Converter<?>> CONVERTERS = new HashMap<>();

        public <T> ConverterRegistry register(final Class<T> type, final ResultSetConversions.Converter<T> converter) {
            CONVERTERS.put(type, converter);
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> ResultSetConversions.Converter<T> get(final Class<T> type) {
            final ResultSetConversions.Converter<?> converter = CONVERTERS.get(type);
            return converter != null ? ((ResultSetConversions.Converter<T>) converter) : null;
        }
    }

    public static final ConverterRegistry CONVERTER_REGISTRY = new ConverterRegistry()
            .register(Boolean.class, ResultSet::getBoolean)
            .register(String.class, ResultSet::getString)
            .register(Integer.class, ResultSet::getInt)
            .register(Double.class, ResultSet::getDouble)
            .register(Float.class, ResultSet::getFloat)
            .register(BigDecimal.class, ResultSet::getBigDecimal);

    @Nullable
    public static <V> V getValueUnsafe(final ResultSet resultSet, final String columnName, final Class<V> valueType) {
        try {
            return getValue(resultSet, columnName, valueType);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static <V> V getValue(final ResultSet resultSet, final String columnName, final Class<V> valueType) throws SQLException {
        if (!SQLHelper.containsColumn(resultSet, columnName))
            throw new NoSuchColumnException("No such column '" + columnName + "'.");

        final Converter<V> converter = CONVERTER_REGISTRY.get(valueType);

        if (converter == null)
            throw new UnrecognisedObjectException("Could not get value from type '" + valueType + "'.");

        return converter.convert(resultSet, columnName);
    }

    public static final class UnrecognisedObjectException extends RuntimeException {
        public UnrecognisedObjectException(String message) {
            super(message);
        }
    }


}
