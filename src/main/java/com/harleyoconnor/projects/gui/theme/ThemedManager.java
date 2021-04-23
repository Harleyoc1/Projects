package com.harleyoconnor.projects.gui.theme;

import com.harleyoconnor.projects.os.SystemManager;
import com.harleyoconnor.serdes.util.Scheduler;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Harley O'Connor
 */
public final class ThemedManager {

    public static final ThemedManager INSTANCE = new ThemedManager();

    private final List<Themed> themedObjects = new LinkedList<>();

    private SystemManager.Theme lastTheme = SystemManager.Theme.LIGHT;

    private ThemedManager() {
        Scheduler.schedule(this::update, Duration.ofSeconds(1));
    }

    public ThemedManager addThemed(final Themed themed) {
        this.themedObjects.add(themed);
        return this;
    }

    private void update() {
        final var newTheme = SystemManager.get().getTheme();

        if (newTheme == this.lastTheme)
            return;

        this.themedObjects.forEach(themed -> themed.themeChange(this.lastTheme, newTheme));
        this.lastTheme = newTheme;
    }

}
