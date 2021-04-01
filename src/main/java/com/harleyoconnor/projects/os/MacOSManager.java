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
            return proc.exitValue() == 0 ? Theme.DARK : Theme.LIGHT;
        } catch (final IOException | InterruptedException e) {
            // If there was an error for whatever reason, log it and default to light mode.
            Logger.getLogger(this.getClass().getName()).warning("Unable to obtain current MacOS theme (defaulting to light). Please report stacktrace below.");
            e.printStackTrace();

            return Theme.LIGHT;
        } catch (final IllegalThreadStateException e) {
            // The operation timed out, so just default to light mode.
            return Theme.LIGHT;
        }
    }

}
