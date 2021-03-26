package com.harleyoconnor.projects.serialisation;

/**
 * An interface used to indicate that a class declaration has a {@link SerDes}
 * associated for serialising and deserialising it. Classes will need to implement
 * this to create a {@link SerDes}.
 *
 * <p>The {@link SerDes} instance will usually be stored in the relevant class as a
 * {@code final}, {@code static} field with {@code public} access.</p>
 *
 * @param <T> The extending {@link Class} type of this.
 * @param <PK> The {@code primary key} type.
 * @author Harley O'Connor
 * @see SerDes
 * @see AbstractSerDesable
 */
public interface SerDesable<T extends SerDesable<T, PK>, PK> {

    /**
     * Gets the {@link SerDes} object for this {@link SerDesable}.
     *
     * @return The {@link SerDes} object.
     */
    SerDes<T, PK> getSerDes ();

}
