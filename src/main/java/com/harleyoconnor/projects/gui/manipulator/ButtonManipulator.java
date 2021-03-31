package com.harleyoconnor.projects.gui.manipulator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * A helper class that helps easily manipulate {@link Button} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <B> The type of the {@link Button} being manipulated.
 * @author Harley O'Connor
 */
public class ButtonManipulator<B extends Button> extends RegionManipulator<B, ButtonManipulator<B>> {

    @SuppressWarnings("unchecked")
    private ButtonManipulator() {
        this((B) new Button());
    }

    private ButtonManipulator(B node) {
        super(node);
    }

    public ButtonManipulator<B> text(String text) {
        this.node.setText(text);
        return this;
    }

    public ButtonManipulator<B> onAction(EventHandler<ActionEvent> eventHandler) {
        this.node.setOnAction(eventHandler);
        return this;
    }

    public static ButtonManipulator<Button> create() {
        return new ButtonManipulator<>();
    }

    public static <B extends Button> ButtonManipulator<B> of(final B button) {
        return new ButtonManipulator<>(button);
    }

}
