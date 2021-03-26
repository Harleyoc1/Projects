package com.harleyoconnor.projects.serialisation;

import com.harleyoconnor.projects.serialisation.fields.Field;
import com.harleyoconnor.projects.serialisation.fields.ForeignField;
import com.harleyoconnor.projects.serialisation.fields.ImmutableField;
import com.harleyoconnor.projects.serialisation.fields.PrimaryField;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Implementations will handle serialising and deserialising an {@link Object} of
 * type {@link T} to and from an SQL database.
 *
 * <p>Implementations will typically contain a {@link Set} of {@link Field} objects,
 * which will both represent a {@link java.lang.reflect.Field} in the Java
 * {@link Class} and a {@code column} in the table.</p>
 *
 * <p>This is named after a SerDes, described as a functional block that serialises
 * and deserialises digital data.</p>
 *
 * <p>All {@link SerDes} objects should be registered via
 * {@link SerDesRegistry#register(SerDes)} for {@link ForeignField} functionality.</p>
 *
 * @param <T> The type for which this instance will handle serialisation and deserialisation.
 * @param <PK> The type of the primary field.
 * @author Harley O'Connor
 * @see Field
 * @see ClassSerDes
 * @see RecordSerDes
 */
public interface SerDes<T extends SerDesable<T, PK>, PK> {

    /**
     * Gets the {@link Class} of the {@link T} type with which this {@link SerDes}
     * handles serialisation and deserialisation for.
     *
     * @return The {@link Class} of {@link T}.
     */
    Class<T> getType();

    /**
     * Gets the name of the SQL {@code table} for this {@link SerDes}.
     *
     * @return The name of the SQL {@code table}.
     */
    String getTable();

    /**
     * Gets the {@link PrimaryField} for this {@link SerDes}.
     *
     * @return The {@link PrimaryField} for this {@link SerDes}.
     */
    PrimaryField<T, PK> getPrimaryField();

    /**
     * Gets all {@link Field} objects for {@link T} as a {@link Collection}.
     *
     * @return A {@link Collection} of {@link Field} objects.
     */
    Collection<Field<T, ?>> getFields();

    /**
     * Gets all {@link ImmutableField} objects for {@link T} as a {@link Collection}.
     *
     * @return A {@link Collection} of {@link ImmutableField} objects.
     */
    Collection<ImmutableField<T, ?>> getImmutableFields();

    /**
     * Gets all {@link ForeignField} objects for this {@link SerDes}.
     *
     * @return A {@link Collection} of {@link ForeignField} objects for this {@link SerDes}.
     */
    Collection<ForeignField<T, ?, ?>> getForeignFields();

    /**
     * Serialises the given {@code object} of type {@link T}, writing the
     * {@link Field} objects back to the database.
     *
     * @param object The {@code object} of type {@link T}.
     */
    void serialise (final T object);

    /**
     * Gets a {@link ResultSet} for the given {@code primaryKeyValue} of type
     * {@link PK}.
     *
     * @param primaryKeyValue The {@code primary key}'s value.
     * @return The {@link ResultSet} obtained.
     */
    ResultSet getResultSet(final PK primaryKeyValue);

    /**
     * Checks if this {@link SerDes} is currently deserialising an object.
     *
     * @return {@code true} if this {@link SerDes} is currently deserialising
     *         an {@link Object}; {@code false} otherwise.
     */
    boolean currentlyDeserialising ();

    /**
     * Adds a {@link Consumer} of type {@link T} which will have
     * {@link Consumer#accept(Object)} called once the next {@link Object}
     * has finished deserialising.
     *
     * @param deserialisationResultConsumer The {@link Consumer} of type {@link T} to run after deserialisation is finished.
     */
    void whenNextDeserialised (Consumer<T> deserialisationResultConsumer);

    /**
     * Calls {@link #getResultSet(Object)} for the given {@code primaryKeyValue}
     * and gives the {@link ResultSet} obtained to {@link #deserialise(ResultSet)}.
     *
     * @param primaryKeyValue The value of the {@code primary key} for the object to deserialise.
     * @return The deserialised {@link Object} of type {@link T}.
     */
    default T deserialise (final PK primaryKeyValue) {
        return this.deserialise(this.getResultSet(primaryKeyValue));
    }

    /**
     * Returns a deserialised {@code object} of type {@link T}, which will be
     * obtained from the given {@link ResultSet}.
     *
     * @param resultSet The {@link ResultSet} to deserialise from.
     * @return The deserialised {@code object} of type {@link T}.
     */
    T deserialise (final ResultSet resultSet);

    /**
     * A careful version of {@link #deserialise(ResultSet)} which doesn't set
     * {@link ForeignField} objects until after their {@link SerDes} is finished
     * deserialising.
     *
     * <p>Note that this method will not check if the {@link ForeignField} object's
     * {@link SerDes} is currently deserialising, that should be checked through its
     * {@link #currentlyDeserialising()} method before calling this.</p>
     *
     * @param resultSet The {@link ResultSet} to deserialise from.
     * @return The deserialised {@code object} of type {@link T}.
     */
    T deserialiseCareful(final ResultSet resultSet);

    default String toString(final T serDesable) {
        final StringBuilder stringBuilder = new StringBuilder(this.getType().getSimpleName()).append("{");

        final Collection<Field<T, ?>> fields = this.getFields();
        final Iterator<Field<T, ?>> fieldIterator = fields.iterator();

        for (int i = 0; i < fields.size(); i++) {
            final Field<T, ?> field = fieldIterator.next();
            stringBuilder.append(field.getName()).append("=").append(field.get(serDesable)).append(i == fields.size() - 1 ? "" : ", ");
        }

        return stringBuilder.append("}").toString();
    }

}
