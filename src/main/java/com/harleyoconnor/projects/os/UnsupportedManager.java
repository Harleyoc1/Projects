package com.harleyoconnor.projects.os;

/**
 * An implementation of {@link SystemManager} for use when the current operating
 * system was not recognised or is unsupported.
 *
 * @author Harley O'Connor
 */
public final class UnsupportedManager implements SystemManager {

    public static final String UNSUPPORTED = "Unsupported";

    /**
     * @return {@link #UNSUPPORTED}.
     */
    @Override
    public String getSystemName() {
        return UNSUPPORTED;
    }

    /**
     * @return {@link Theme#LIGHT}
     */
    @Override
    public Theme getTheme() {
        return Theme.LIGHT;
    }

}
