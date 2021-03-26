package com.harleyoconnor.projects.serialisation.exceptions;

/**
 * Thrown when a {@link com.harleyoconnor.projects.serialisation.SerDes} that was expected to be registered was not.
 *
 * @author Harley O'Connor
 */
public final class NoSuchSerDesException extends RuntimeException {

    /**
     * Constructs a {@link NoSuchSerDesException} without a detail message.
     */
    public NoSuchSerDesException() {}

    /**
     * Constructs a {@link NoSuchSerDesException} with a detail message.
     *
     * @param message The detail message.
     */
    public NoSuchSerDesException(final String message) {
        super(message);
    }

}
