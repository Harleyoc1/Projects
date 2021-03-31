package com.harleyoconnor.projects.gui.builder;

import javafx.scene.layout.StackPane;

/**
 * A helper class that helps easily manipulate {@link StackPane} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <S> The type of the {@link StackPane} being manipulated.
 * @author Harley O'Connor
 */
public class StackPaneManipulator<S extends StackPane> extends PaneManipulator<S, StackPaneManipulator<S>> {

    public StackPaneManipulator(S stackPane) {
        super(stackPane);
    }

    public static StackPaneManipulator<StackPane> create () {
        return new StackPaneManipulator<>(new StackPane());
    }

    public static StackPaneManipulator<StackPane> edit(StackPane stackPane) {
        return new StackPaneManipulator<>(stackPane);
    }

}
