package com.harleyoconnor.projects.serialisation;

import com.google.common.collect.ImmutableSet;
import com.harleyoconnor.projects.serialisation.fields.*;
import com.harleyoconnor.projects.serialisation.util.ResultSetConversions;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Main implementation of {@link SerDes}, holding an {@link ImmutableSet} of {@link Field}
 * objects - meaning they can either be a hard or soft field.
 *
 * <p>This {@link SerDes} must be instantiated using {@link Builder}.</p>
 *
 * @param <T> The type for which this instance will handle serialisation and deserialisation.
 * @param <PK> The type of the primary field.
 * @author Harley O'Connor
 * @see SerDes
 * @see RecordSerDes
 */
public final class ClassSerDes<T extends SerDesable<T, PK>, PK> extends AbstractSerDes<T, PK> {

    private final ImmutableSet<Field<T, ?>> fields;

    private ClassSerDes(final Class<T> type, final String table, final PrimaryField<T, PK> primaryField, final Set<Field<T, ?>> fields) {
        super(type, table, primaryField);
        fields.add(primaryField);
        this.fields = ImmutableSet.copyOf(fields);
    }

    @Override
    public Set<Field<T, ?>> getFields() {
        return fields;
    }

    @Override
    protected T finaliseDeserialisation(ResultSet resultSet, T constructedObject, boolean careful) {
        this.getMutableFields().forEach(mutableField -> this.setField(resultSet, constructedObject, mutableField));

        if (!careful)
            this.getForeignFields().forEach(foreignField -> this.setField(resultSet, constructedObject, foreignField));
        else this.getForeignFields().forEach(foreignField -> foreignField.getParentSerDes().get()
                .whenNextDeserialised(deserialisedObject -> this.whenNextDeserialised(constructedObject, deserialisedObject, foreignField)));

        return super.finaliseDeserialisation(resultSet, constructedObject, careful);
    }

    @SuppressWarnings("unchecked")
    private <SD extends SerDesable<SD, ?>, V> void whenNextDeserialised(final T object, final SD deserialisedObject, final ForeignField<T, V, ?> foreignField) {
        deserialisedObject.getSerDes().getFields().stream().filter(field -> field.equals(foreignField.getForeignField())).map(field -> ((Field<SD, V>) field)).forEach(field ->
                foreignField.set((T) field.get(deserialisedObject), foreignField.get(object))
        );
    }

    private <V> void setField(final ResultSet resultSet, final T object, final Field<T, V> field) {
        field.set(object, ResultSetConversions.getValueUnsafe(resultSet, field.getName(), field.getFieldType()));
    }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends SerDesable<T, PK>, PK, CSD extends ClassSerDes<T, PK>, B extends ClassSerDes.Builder<T, PK, CSD, B>> extends AbstractSerDes.Builder<T, PK, CSD, B> {

        private final Set<Field<T, ?>> fields = new HashSet<>();

        public Builder(final Class<T> type, final String tableName) {
            super(type, tableName);
        }

        public <FT> B field(final String name, final Class<FT> fieldType, final Function<T, FT> getter, final BiConsumer<T, FT> setter) {
            this.fields.add(new MutableField<>(name, this.type, fieldType, false, getter, setter));
            return (B) this;
        }

        public <FT> B uniqueField(final String name, final Class<FT> fieldType, final Function<T, FT> getter, final BiConsumer<T, FT> setter) {
            this.fields.add(new MutableField<>(name, this.type, fieldType, true, getter, setter));
            return (B) this;
        }

        public <FKT extends SerDesable<FKT, ?>, FT> B foreignField(final String name, final Field<FKT, FT> foreignField, final Function<T, FKT> getter, final BiConsumer<T, FKT> setter) {
            this.fields.add(new MutableForeignField<>(name, this.type, foreignField, false, getter, setter));
            return (B) this;
        }

        public <FKT extends SerDesable<FKT, ?>, FT> B uniqueForeignField(final String name, final Field<FKT, FT> foreignField, final Function<T, FKT> getter, final BiConsumer<T, FKT> setter) {
            this.fields.add(new MutableForeignField<>(name, this.type, foreignField, true, getter, setter));
            return (B) this;
        }

        @Override
        public CSD build () {
            this.assertPrimaryFieldSet();
            return this.register((CSD) new ClassSerDes<>(this.type, this.tableName, this.primaryField, this.fields));
        }

        public static <T extends SerDesable<T, PK>, PK, CSD extends ClassSerDes<T, PK>, B extends ClassSerDes.Builder<T, PK, CSD, B>> Builder<T, PK, CSD, B> of(final Class<T> type, final Class<PK> primaryKeyClass) {
            return of(type, primaryKeyClass, type.getSimpleName() + "s");
        }

        public static <T extends SerDesable<T, PK>, PK, CSD extends ClassSerDes<T, PK>, B extends ClassSerDes.Builder<T, PK, CSD, B>> Builder<T, PK, CSD, B> of(final Class<T> type, final Class<PK> primaryKeyClass, final String tableName) {
            return new Builder<>(type, tableName);
        }

    }

}
