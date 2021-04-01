package com.harleyoconnor.projects.gui.stylesheets;

import com.harleyoconnor.projects.os.SystemManager;
import javafx.collections.ObservableList;

import java.util.Objects;

/**
 * Represents a {@link Stylesheet} which has a separate stylesheet when dark mode is enabled.
 * If {@link #mutuallyExclusive} is {@code true} then when the theme changes the last
 * stylesheet is removed and the relevant new one added, and when {@code false} the light
 * stylesheet is always retained (whilst the dark one will still be removed).
 *
 * @author Harley O'Connor
 */
public final class ThemedStylesheet extends Stylesheet {

    private static final String DEFAULT_DARK_SUFFIX = "-dark";

    private final String darkPath;
    private final boolean mutuallyExclusive;

    public ThemedStylesheet(final String path) {
        this(path, path + DEFAULT_DARK_SUFFIX);
    }

    public ThemedStylesheet(final String path, final String darkPath) {
        this(path, darkPath, false);
    }

    public ThemedStylesheet(final String path, final boolean mutuallyExclusive) {
        this(path, path + DEFAULT_DARK_SUFFIX, mutuallyExclusive);
    }

    public ThemedStylesheet(final String path, final String darkPath, final boolean mutuallyExclusive) {
        super(path);
        this.darkPath = this.checkAndCorrectPath(darkPath);
        this.mutuallyExclusive = mutuallyExclusive;
    }

    @Override
    public Stylesheet add(ObservableList<String> stylesheets) {
        return this;
    }

    public void add(final ObservableList<String> stylesheets, final SystemManager.Theme theme) {
        stylesheets.add(this.pathForTheme(theme));
    }

    public void themeChanged(final ObservableList<String> stylesheets, final SystemManager.Theme lastTheme, final SystemManager.Theme newTheme) {
        // If the stylesheets are not mutually exclusive and the new theme is light, disable dark stylesheet.
        if (!this.mutuallyExclusive && newTheme == SystemManager.Theme.LIGHT)
            stylesheets.remove(this.darkPath);
        // If the stylesheets are mutually exlusive, remove the last theme.
        else if (this.mutuallyExclusive)
            stylesheets.remove(this.pathForTheme(lastTheme));

        // Add the theme for the new stylesheet.
        this.add(stylesheets, newTheme);
    }

    private String pathForTheme (final SystemManager.Theme theme) {
        return theme == SystemManager.Theme.LIGHT ? this.path : this.darkPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        final ThemedStylesheet that = (ThemedStylesheet) o;
        return this.mutuallyExclusive == that.mutuallyExclusive && Objects.equals(this.darkPath, that.darkPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.darkPath, this.mutuallyExclusive);
    }
}
