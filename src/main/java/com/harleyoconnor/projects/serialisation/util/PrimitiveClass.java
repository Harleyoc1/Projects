package com.harleyoconnor.projects.serialisation.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harley O'Connor
 */
public final class PrimitiveClass {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_CLASSES = new HashMap<>();

    static {
        PRIMITIVE_CLASSES.put(Boolean.class, Boolean.TYPE);
        PRIMITIVE_CLASSES.put(Byte.class, Byte.TYPE);
        PRIMITIVE_CLASSES.put(Short.class, Short.TYPE);
        PRIMITIVE_CLASSES.put(Integer.class, Integer.TYPE);
        PRIMITIVE_CLASSES.put(Long.class, Long.TYPE);
        PRIMITIVE_CLASSES.put(Float.class, Float.TYPE);
        PRIMITIVE_CLASSES.put(Double.class, Double.TYPE);
        PRIMITIVE_CLASSES.put(Character.class, Character.TYPE);
    }

    /**
     * Checks if the given {@link Class} is convertible to a primitive class by
     * {@link #convert(Class)}.
     *
     * @param clazz The {@link Class} to check.
     * @return {@code true} if it can be converted; {@code false} otherwise.
     */
    public static boolean convertible(final Class<?> clazz) {
        return PRIMITIVE_CLASSES.containsKey(clazz);
    }

    /**
     * Converts the given {@link Class} to its primitive {@link Class} and returns it, or
     * returns the given {@link Class} if it did not have a primitive type.
     *
     * @param clazz The {@link Class} to convert.
     * @return The primitive {@link Class} if a converter is registered; otherwise the
     *         given {@link Class}.
     */
    public static Class<?> convert(final Class<?> clazz) {
        return PRIMITIVE_CLASSES.getOrDefault(clazz, clazz);
    }

}
