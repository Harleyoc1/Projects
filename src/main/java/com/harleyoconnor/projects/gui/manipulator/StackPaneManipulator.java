package com.harleyoconnor.projects.gui.manipulator;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * A helper class that helps easily manipulate {@link StackPane} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <S> The type of the {@link StackPane} being manipulated.
 * @author Harley O'Connor
 */
public class StackPaneManipulator<S extends StackPane> extends PaneManipulator<S, StackPaneManipulator<S>> {

    @SuppressWarnings("unchecked")
    private StackPaneManipulator() {
        this((S) new StackPane());
    }

    private StackPaneManipulator(S stackPane) {
        super(stackPane);
    }

    @SuppressWarnings("unchecked")
    public <P extends Pane, PM extends PaneManipulator<P, PM>> PaneManipulator<P, PM> toPaneManipulator() {
        return (PaneManipulator<P, PM>) this;
    }

    public static StackPaneManipulator<StackPane> create () {
        return new StackPaneManipulator<>();
    }

    public static <S extends StackPane> StackPaneManipulator<S> of(S stackPane) {
        return new StackPaneManipulator<>(stackPane);
    }

}
