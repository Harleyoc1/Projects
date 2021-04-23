package com.harleyoconnor.projects.gui.theme;

import com.harleyoconnor.projects.os.SystemManager;

/**
 * Represents something that changes depending on the current {@link SystemManager.Theme}.
 *
 * @author Harley O'Connor
 */
public interface Themed {

    /**
     * Runs an action when the {@link SystemManager.Theme} changes.
     *
     * @param oldTheme The last {@link SystemManager.Theme}.
     * @param newTheme The new {@link SystemManager.Theme}.
     */
    void themeChange(final SystemManager.Theme oldTheme, final SystemManager.Theme newTheme);

}
