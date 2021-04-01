package com.harleyoconnor.projects.gui.stylesheets;

import com.harleyoconnor.projects.os.SystemManager;
import com.harleyoconnor.projects.util.Scheduler;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages {@link Stylesheet} {@link Object}s.
 *
 * @author Harley O'Connor
 */
public final class StylesheetManager {

    /**
     * Stores {@link ObservableList}s of {@link Stylesheet} paths and their {@link Stylesheet} objects.
     * Stored in a {@link WeakHashMap}, meaning that when the stylesheets are garbage collected the entry
     * is removed from the {@link Map} automatically.
     */
    private final Map<ObservableList<String>, Set<Stylesheet>> stylesheets = new WeakHashMap<>();

    private SystemManager.Theme lastTheme = SystemManager.Theme.LIGHT;

    public StylesheetManager() {
        // Schedules a call of #update() every second.
        Scheduler.schedule(this::update, Duration.ofSeconds(1));
    }

    public void addStylesheet(final ObservableList<String> stylesheets, final Stylesheet stylesheet) {
        this.stylesheets.computeIfAbsent(stylesheets, k -> new HashSet<>()).add(stylesheet.add(stylesheets));
    }

    public void addStylesheets(final ObservableList<String> stylesheets, final Stylesheet... stylesheetsToAdd) {
        this.stylesheets.computeIfAbsent(stylesheets, k -> new HashSet<>()).addAll(Stream.of(stylesheetsToAdd).map(stylesheet -> stylesheet.add(stylesheets)).collect(Collectors.toSet()));
    }

    /**
     * Updates {@link ThemedStylesheet}. Called by a {@link TimerTask} set in
     * {@link #StylesheetManager()} every second.
     */
    private void update() {
        final var newTheme = SystemManager.get().getTheme();

        if (newTheme != this.lastTheme) {
            this.stylesheets.forEach((stylesheetList, stylesheets) -> {
                stylesheets.forEach(stylesheet -> {
                    if (!(stylesheet instanceof ThemedStylesheet))
                        return;

                    ((ThemedStylesheet) stylesheet).themeChanged(stylesheetList, this.lastTheme, newTheme);
                });
            });

            this.lastTheme = newTheme;
        }
    }

}
