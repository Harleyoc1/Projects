package com.harleyoconnor.projects.gui.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * A helper class that helps easily manipulate {@link Button} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <B> The type of the {@link Button} being manipulated.
 * @author Harley O'Connor
 */
public class ButtonManipulator<B extends Button> extends RegionManipulator<B, ButtonManipulator<B>> {

    public ButtonManipulator(B node) {
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
        return new ButtonManipulator<>(new Button());
    }

}
