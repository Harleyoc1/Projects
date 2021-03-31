package com.harleyoconnor.projects.util;

import java.lang.annotation.*;

/**
 * An informative annotation type used to indicate that a {@code final} {@link java.lang.reflect.Field}
 * will have its value injected through reflection.
 *
 * @author Harley O'Connor
 */
@Documented
@Target(ElementType.FIELD)
public @interface Injected { }
