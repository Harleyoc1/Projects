package com.harleyoconnor.projects.gui.stylesheets;

import com.harleyoconnor.projects.os.SystemManager;
import com.harleyoconnor.serdes.util.Scheduler;
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
// TODO: Restructure themed stylesheets to update from `ThemedManager`.
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

    public void addStylesheets(final ObservableList<String> stylesheets, final Stylesheet... stylesheetsToAdd) {
        // Add the given sheet paths to the given stylesheets and collect them into a set.
        final Set<Stylesheet> sheetsToAdd = Stream.of(stylesheetsToAdd).map(stylesheet -> stylesheet.add(stylesheets)).collect(Collectors.toUnmodifiableSet());

        // Update their theme, in case its not set to their default.
        this.updateThemedStylesheets(this.lastTheme, stylesheets, sheetsToAdd);

        // Add the stylesheets to the main stylesheet map.
        this.stylesheets.computeIfAbsent(stylesheets, k -> new HashSet<>()).addAll(sheetsToAdd);
    }

    /**
     * Updates {@link ThemedStylesheet}. Called by a {@link TimerTask} set in
     * {@link #StylesheetManager()} every second.
     */
    private void update() {
        final var newTheme = SystemManager.get().getTheme();

        if (newTheme != this.lastTheme) {
            this.stylesheets.forEach((stylesheetList, stylesheets) ->
                    this.updateThemedStylesheets(newTheme, stylesheetList, stylesheets));

            this.lastTheme = newTheme;
        }
    }

    private void updateThemedStylesheets(SystemManager.Theme newTheme, ObservableList<String> stylesheetList, Set<Stylesheet> stylesheets) {
        stylesheets.stream().filter(ThemedStylesheet.class::isInstance).forEach(stylesheet ->
                ((ThemedStylesheet) stylesheet).themeChanged(stylesheetList, this.lastTheme, newTheme));
    }

}
