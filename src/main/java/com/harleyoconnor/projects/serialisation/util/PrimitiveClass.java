package com.harleyoconnor.projects.serialisation.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Harley O'Connor
 */
public final class PrimitiveClass {

    private static final Map<Class<?>, Supplier<Class<?>>> PRIMITIVE_CLASS_SUPPLIERS = new HashMap<>();

    static {
        PRIMITIVE_CLASS_SUPPLIERS.put(Boolean.class, () -> Boolean.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Byte.class, () -> Byte.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Short.class, () -> Short.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Integer.class, () -> Integer.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Long.class, () -> Long.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Float.class, () -> Float.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Double.class, () -> Double.TYPE);
        PRIMITIVE_CLASS_SUPPLIERS.put(Character.class, () -> Character.TYPE);
    }

    /**
     * Checks if the given {@link Class} is convertible to a primitive class by
     * {@link #convert(Class)}.
     *
     * @param clazz The {@link Class} to check.
     * @return {@code true} if it can be converted; {@code false} otherwise.
     */
    public static boolean convertible(final Class<?> clazz) {
        return PRIMITIVE_CLASS_SUPPLIERS.containsKey(clazz);
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
        return PRIMITIVE_CLASS_SUPPLIERS.getOrDefault(clazz, () -> clazz).get();
    }

}
