package com.harleyoconnor.projects.os;

/**
 * A {@link SystemManager} handles operations that are specific to the operating system
 * the program is currently running on, such as getting the current theme
 * (which can be done via {@link #getTheme()}).
 *
 * <p>The main {@link SystemManager} instance for the current system is stored in
 * {@link #SYSTEM_MANAGER} (which can also be accessed through {@link #get()} for
 * convenience).</p>
 *
 * @author Harley O'Connor
 */
public interface SystemManager {

    /** Stores the {@link SystemManager} for the current system. */
    SystemManager SYSTEM_MANAGER = getForOS();

    /**
     * Gets the operating system's name. This should match the result of a call to
     * {@link System#getProperty(String)} with {@code os.name}.
     *
     * @return The name for the operating system.
     */
    String getSystemName();

    /**
     * An enum stating the current theme/appearance set in the operating system.
     */
    enum Theme {
        LIGHT, DARK
    }

    /**
     * Gets the current {@link Theme} for the operating system.
     *
     * @return The current {@link Theme}, or {@link Theme#LIGHT} as a default if
     *         it could not be found.
     */
    Theme getTheme();

    static SystemManager get() {
        return SYSTEM_MANAGER;
    }

    private static SystemManager getForOS() {
        return switch (System.getProperty("os.name")) {
            case MacOSManager.NAME -> new MacOSManager();
            default -> new UnsupportedManager();
        };
    }

}
