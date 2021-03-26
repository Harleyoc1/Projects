package com.harleyoconnor.projects.serialisation.fields;

import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.serialisation.SerDes;
import com.harleyoconnor.projects.serialisation.SerDesRegistry;
import com.harleyoconnor.projects.serialisation.SerDesable;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class provides a skeletal implementation of the {@link ForeignField} interface, to minimise the
 * effort required to implement it.
 *
 * <p>Its main use is to provide common fields ({@link #foreignField} and {@link #getter}) and to provide
 * simple implementations for {@link #getFromValue(Object)} and {@link #get(SerDesable)}.</p>
 *
 * @param <P> The type of the parent {@link Class}.
 * @param <T> The type of the foreign {@code table}'s {@code field}.
 * @param <FKT> The type of the foreign {@code table}.
 * @author Harley O'Connor
 * @see AbstractField
 * @see ForeignField
 */
public abstract class AbstractForeignField<P extends SerDesable<P, ?>, T, FKT extends SerDesable<FKT, ?>> extends AbstractField<P, T> implements ForeignField<P, T, FKT> {

    /** The {@code foreign field} - the {@link Field} that this {@link ForeignField} {@code references}. */
    protected final Field<FKT, T> foreignField;

    /** A {@link Function} getter for {@link FKT}. */
    private final Function<P, FKT> getter;

    @SuppressWarnings("all")
    public AbstractForeignField(String name, Class<P> parentType, Field<FKT, T> foreignField, boolean unique, Function<P, FKT> getter) {
        super(name, parentType, foreignField.getFieldType(), unique, null);
        this.foreignField = foreignField;
        this.getter = getter;
    }

    @Override
    public Field<FKT, T> getForeignField() {
        return this.foreignField;
    }

    @Override
    public T get(P object) {
        return this.foreignField.get(this.getter.apply(object));
    }

    @Override
    public FKT getFromValue(T value) {
        /* We call unsafe here as this should not be called unless there is already a SerDes registered
           for the foreign field, and if not that is a misuse of the API. */
        final SerDes<FKT, ?> serDes = SerDesRegistry.getUnsafe(this.foreignField.getParentType());

        // Selects the result set from the database based on the given value.
        final ResultSet resultSet = Projects.getDatabaseController().select(serDes.getTable(), this.foreignField.getName(), value);

        final FKT deserialisedObject;

        /* If the SerDes is currently deserialising an object, call the careful deserialise method
           (which queues foreign fields until after the currently field has loaded to prevent infinite
           loops). */
        if (serDes.currentlyDeserialising())
            deserialisedObject = serDes.deserialiseCareful(resultSet);
        else deserialisedObject = serDes.deserialise(resultSet);

        return deserialisedObject;
    }

}
