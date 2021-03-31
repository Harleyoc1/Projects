package com.harleyoconnor.projects.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Harley O'Connor
 */
public final class ReflectionHelper {

    public static void setFieldUnchecked(final Object object, final String name, final Object value) {
        try {
            setField(object, name, value);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(final Object object, final String name, final Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = setAccessible(getDeclaredField(object.getClass(), name));
        field.set(object, value);
    }

    public static void setFieldUnchecked(final Class<?> clazz, final String name, final Object value) {
        try {
            setField(clazz, name, value);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(final Class<?> clazz, final String name, final Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = setAccessible(getDeclaredField(clazz, name));
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            final Unsafe unsafe = getUnsafe();
            unsafe.putObject(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), value);
        } else {
            field.set(null, value);
        }
    }

    public static Field setAccessible(final Field field) {
        field.setAccessible(true);
        return field;
    }

    public static Field getDeclaredFieldUnchecked(final Class<?> clazz, final String name) {
        try {
            return getDeclaredField(clazz, name);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getDeclaredField(final Class<?> clazz, final String name) throws NoSuchFieldException {
        return clazz.getDeclaredField(name);
    }

    public static Field getFieldUnchecked(final Object object, final String name) {
        try {
            return object.getClass().getField(name);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(final Object object, final String name) throws NoSuchFieldException {
        return object.getClass().getField(name);
    }

    public static Unsafe getUnsafe () throws NoSuchFieldException, IllegalAccessException {
        final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        return (Unsafe) unsafeField.get(null);
    }

}
