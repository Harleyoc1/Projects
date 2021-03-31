package com.harleyoconnor.projects.serialisation;

import com.harleyoconnor.javautilities.pair.Pair;
import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.serialisation.exception.NoSuchConstructorException;
import com.harleyoconnor.projects.serialisation.field.Field;
import com.harleyoconnor.projects.serialisation.field.ForeignField;
import com.harleyoconnor.projects.serialisation.field.ImmutableField;
import com.harleyoconnor.projects.serialisation.field.PrimaryField;
import com.harleyoconnor.projects.serialisation.util.PrimitiveClass;
import com.harleyoconnor.projects.serialisation.util.ResultSetConversions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class provides a skeletal implementation of the {@link SerDes} interface, to minimise the
 * effort required to implement it.
 *
 * @param <T> The type for which this instance will handle serialisation and deserialisation.
 * @param <PK> The type of the primary field.
 * @author Harley O'Connor
 * @see SerDes
 * @see ClassSerDes
 * @see RecordSerDes
 */
public abstract class AbstractSerDes<T extends SerDesable<T, PK>, PK> implements SerDes<T, PK> {

    protected final Class<T> type;
    protected final String table;
    protected final PrimaryField<T, PK> primaryField;

    protected final Set<ImmutableField<T, ?>> immutableFields;

    // TODO: A way of automatic unloading these (maybe add a WeakHashSet to JavaUtilities?)
    protected final Set<T> loadedObjects = new HashSet<>();

    protected final List<Consumer<T>> nextDeserialisedResultConsumers = new ArrayList<>();
    private boolean currentlyDeserialising;

    public AbstractSerDes(Class<T> type, String table, PrimaryField<T, PK> primaryField, Set<ImmutableField<T, ?>> immutableFields) {
        this.type = type;
        this.table = table;
        this.primaryField = primaryField;
        this.immutableFields = immutableFields;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public PrimaryField<T, PK> getPrimaryField() {
        return this.primaryField;
    }

    @Override
    public Set<T> getLoadedObjects() {
        return this.loadedObjects;
    }

    @Override
    public Set<Field<T, ?>> getImmutableFields() {
        return this.immutableFields.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void serialise(T object) {
        final var controller = Projects.getDatabaseController();

        // If it doesn't already exist, insert the new value.
        if (!controller.valueExists(this.table, this.primaryField.getName(), this.primaryField.get(object))) {
            controller.insertUnsafe(this.table, this.toInsertableValuePair(object, this.getFields()));
            return;
        }

        // Otherwise, update the value.
        controller.updateUnsafe(this.table, this.primaryField.getName(), this.primaryField.get(object),
                this.toInsertableValuePair(object, this.getMutableFields().stream().map(field -> ((Field<T, ?>) field)).collect(Collectors.toUnmodifiableSet())));
    }

    @SuppressWarnings("unchecked")
    private Pair<String, Object>[] toInsertableValuePair(final T object, final Set<Field<T, ?>> fields) {
        return (Pair<String, Object>[]) fields.stream().map(field -> Pair.immutable(field.getName(), field.get(object))).toArray(Pair<?, ?>[]::new);
    }

    @Override
    public boolean currentlyDeserialising() {
        return this.currentlyDeserialising;
    }

    @Override
    public void whenNextDeserialised(Consumer<T> deserialisationResultConsumer) {
        this.nextDeserialisedResultConsumers.add(deserialisationResultConsumer);
    }

    @Override
    public ResultSet getResultSet(PK primaryKeyValue) {
        return Projects.getDatabaseController().selectUnsafe(this.table, this.primaryField.getName(), primaryKeyValue);
    }

    @Override
    public T deserialise(ResultSet resultSet, boolean careful) {
        this.currentlyDeserialising = true;

        final Constructor<T> constructor;

        try {
            constructor = this.type.getConstructor(this.immutableFields.stream().map(field -> PrimitiveClass.convert(field.getFieldType())).toArray(Class<?>[]::new));
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(NoSuchConstructorException.from(e));
        }

        try {
            final var args = new ArrayList<>();

            immutableFields.forEach(field ->
                    args.add(ResultSetConversions.getValueUnsafe(resultSet, field.getName(), field.getFieldType())));

            final T constructedObject = constructor.newInstance(args.toArray());
            this.loadedObjects.add(constructedObject);

            return this.finaliseDeserialisation(resultSet, constructedObject, careful);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finalises deserialisation by running additional tasks after an {@link Object}
     * of type {@link T} has been constructed.
     *
     * @param resultSet The {@link ResultSet} the {@code constructedObject} was deserialised from.
     * @param constructedObject The constructed {@link Object} of type {@link T}.
     * @param careful {@code true} if {@link ForeignField} objects shouldn't be
     *                            set until after its {@link SerDes} is finished
     *                            deserialising (to avoid infinite loops).
     * @return The constructed {@code constructedObject} for in-line calls.
     */
    protected T finaliseDeserialisation (ResultSet resultSet, T constructedObject, boolean careful) {
        this.currentlyDeserialising = false;

        this.nextDeserialisedResultConsumers.forEach(consumer -> consumer.accept(constructedObject));
        this.nextDeserialisedResultConsumers.clear();

        return constructedObject;
    }

    @Override
    public T deserialiseCareful(ResultSet resultSet) {
        return this.deserialise(resultSet);
    }

    @SuppressWarnings("unchecked")
    protected abstract static class Builder<T extends SerDesable<T, PK>, PK, SD extends AbstractSerDes<T, PK>, B extends Builder<T, PK, SD, B>> {
        protected final Class<T> type;
        protected final String tableName;

        /** The {@link ImmutableField}s, stored in a {@link LinkedHashMap} to retain the order in which they are added. */
        protected final LinkedHashSet<ImmutableField<T, ?>> immutableFields = new LinkedHashSet<>();

        protected final Set<Field<T, ?>> fields = new HashSet<>();

        protected PrimaryField<T, PK> primaryField;

        public Builder(Class<T> type, String tableName) {
            this.type = type;
            this.tableName = tableName;
        }

        public B primaryField(final String name, final Class<PK> fieldType, final Function<T, PK> getter) {
            return this.primaryField(new PrimaryField<>(name, this.type, fieldType, getter));
        }

        public B primaryField(final PrimaryField<T, PK> primaryField) {
            this.primaryField = primaryField;
            this.immutableFields.add(this.primaryField);
            return (B) this;
        }

        public <FT> B field(final Field<T, FT> field) {
            if (!field.isMutable())
                this.immutableFields.add(((ImmutableField<T, ?>) field));
            else this.fields.add(field);

            return (B) this;
        }

        public <FT> B field(final String name, final Class<FT> fieldType, final Function<T, FT> getter) {
            return this.field(new ImmutableField<>(name, this.type, fieldType, false, getter));
        }

        public <FT> B uniqueField(final String name, final Class<FT> fieldType, final Function<T, FT> getter) {
            return this.field(new ImmutableField<>(name, this.type, fieldType, true, getter));
        }

        public abstract SD build();

        public void assertPrimaryFieldSet() {
            if (this.primaryField == null)
                throw new PrimaryFieldUnset("Primary field was not set in SerDes Builder for '" + this.type.getSimpleName() + "'.");
        }

        public SD register(SD serDes) {
            SerDesRegistry.register(serDes);
            return serDes;
        }
    }

    protected static final class PrimaryFieldUnset extends RuntimeException {
        public PrimaryFieldUnset(String message) {
            super(message);
        }
    }

}
