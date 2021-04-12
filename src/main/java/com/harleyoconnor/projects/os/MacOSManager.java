package com.harleyoconnor.projects.os;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * An implementation of {@link SystemManager} for MacOS.
 *
 * @author Harley O'Connor
 */
public final class MacOSManager implements SystemManager {

    public static final String NAME = "Mac OS X";

    /**
     * {@inheritDoc}
     *
     * @return {@link #NAME}.
     */
    @Override
    public String getSystemName() {
        return NAME;
    }

    private Theme lastTheme = Theme.LIGHT;

    /**
     * Updates {@link #lastTheme} and returns the given {@link Theme} for
     * in-line calls.
     *
     * @param currentTheme The current {@link Theme}.
     * @return The {@code currentTheme} given.
     */
    private Theme updateLastTheme(final Theme currentTheme) {
        return this.lastTheme = currentTheme;
    }

    /**
     * {@inheritDoc}
     *
     * @return The current {@link Theme}, or {@link Theme#LIGHT} as a default if
     *         it could not be found.
     */
    @Override
    public Theme getTheme() {
        try {
            final Process proc = Runtime.getRuntime().exec(new String[]{"defaults", "read", "-g", "AppleInterfaceStyle"});
            proc.waitFor(500, TimeUnit.MILLISECONDS);
            return this.updateLastTheme(proc.exitValue() == 0 ? Theme.DARK : Theme.LIGHT);
        } catch (final IOException | InterruptedException e) {
            // If there was an error for whatever reason, log it and default to whatever the last detected theme was.
            Logger.getLogger(this.getClass().getName()).warning("Unable to obtain current MacOS theme (defaulting to light). Please report stacktrace below.");
            e.printStackTrace();

            return this.lastTheme;
        } catch (final IllegalThreadStateException e) {
            // The operation timed out, so just default to whatever the last detected theme was.
            return this.lastTheme;
        }
    }

}
