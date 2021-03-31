package com.harleyoconnor.projects.gui.manipulator;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javax.annotation.Nonnull;

/**
 * A helper class that helps easily manipulate {@link Scene} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <S> The type of the {@link Scene} being manipulated.
 * @author Harley O'Connor
 */
public class SceneManipulator<S extends Scene> implements ChainManipulator<S> {

    private final S scene;

    @SuppressWarnings("unchecked")
    private SceneManipulator(final Parent initialRoot) {
        this((S) new Scene(initialRoot));
    }

    private SceneManipulator(S scene) {
        this.scene = scene;
    }

    public SceneManipulator<S> root(final Parent parent) {
        this.scene.setRoot(parent);
        return this;
    }

    @Nonnull
    @Override
    public S get() {
        return this.scene;
    }

    public static SceneManipulator<Scene> create (final Parent initialRoot) {
        return new SceneManipulator<>(initialRoot);
    }

    public static <S extends Scene> SceneManipulator<S> of(S stackPane) {
        return new SceneManipulator<>(stackPane);
    }

}
