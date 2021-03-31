package com.harleyoconnor.projects.gui.manipulator;

import javax.annotation.Nonnull;

/**
 * @author Harley O'Connor
 */
// TODO: Move manipulators and other similar functionality into its own library.
public interface ChainManipulator<O> {

    /**
     * @return The {@link Object} of type {@link O} being manipulated.
     */
    @Nonnull
    O get();

}
