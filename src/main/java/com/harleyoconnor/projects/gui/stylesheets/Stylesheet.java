package com.harleyoconnor.projects.gui.stylesheets;

import com.harleyoconnor.javautilities.util.FileUtils;
import com.harleyoconnor.projects.Constants;
import javafx.collections.ObservableList;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Represents a {@code CSS stylesheet} for use in a {@code JavaFX} environment.
 *
 * @author Harley O'Connor
 */
public class Stylesheet {

    protected final String path;

    /**
     * Constructs a new {@link Stylesheet} {@link Object} with the given {@code path}.
     *
     * @param path The stylesheet's path relative to {@code resources/assets/stylesheets}.
     */
    public Stylesheet(final String path) {
        this.path = this.checkAndCorrectPath(path);
    }

    /**
     * Checks a file exists at the given {@code path} and corrects it to a full path.
     * Note that it also adds the {@code .css} extension if it is not already there.
     *
     * @param path The stylesheet's path relative to {@code resources/assets/stylesheets}.
     * @return The corrected, full {@code path}.
     */
    protected final String checkAndCorrectPath (final String path) {
        final var file = FileUtils.getFile(Constants.STYLESHEETS + path + (!path.endsWith(Constants.CSS) ? Constants.CSS : ""));

        if (!file.exists()) {
            Logger.getLogger(this.getClass().getName()).warning("Stylesheet '" + file.getPath() + "' does not exist.");
            return path;
        }

        return Constants.FILE + file.getPath();
    }

    /**
     * Adds this {@link Stylesheet} to the given {@code stylesheets} {@link ObservableList}.
     *
     * @param stylesheets The {@code stylesheets} {@link ObservableList} to add this {@link Stylesheet} to.
     * @return This {@link Stylesheet} for chaining or in-line calls.
     */
    public Stylesheet add(final ObservableList<String> stylesheets) {
        stylesheets.add(this.path);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        return Objects.equals(this.path, ((Stylesheet) obj).path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path);
    }

}
