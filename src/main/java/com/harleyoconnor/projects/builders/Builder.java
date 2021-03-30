package com.harleyoconnor.projects.builders;

import javax.annotation.Nonnull;

/**
 * @author Harley O'Connor
 */
// TODO: Move builders and other functionality into its own library.
public interface Builder<B> {

    /**
     * @return The {@link Object} that's been built.
     */
    @Nonnull
    B build ();

}
