package com.harleyoconnor.projects.gui.builder;

import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * A helper class that helps easily manipulate {@link Timeline} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <T> The type of the {@link TextField} being manipulated.
 * @author Harley O'Connor
 */
public class TextFieldManipulator<T extends TextField> extends RegionManipulator<T, TextFieldManipulator<T>> {

    @SuppressWarnings("unchecked")
    private TextFieldManipulator() {
        this((T) new TextField());
    }

    private TextFieldManipulator(T textField) {
        super(textField);
    }

    /**
     * Executes the given {@link EventHandler} when the enter key is pressed.
     *
     * @param eventHandler The {@link EventHandler} object to call.
     * @return This {@link TextFieldManipulator} object.
     */
    public TextFieldManipulator<T> onEnter (EventHandler<Event> eventHandler) {
        this.node.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            eventHandler.handle(event);
        });
        return this;
    }

    public TextFieldManipulator<T> placeholder(String text) {
        this.node.setPromptText(text);
        return this;
    }

    public TextFieldManipulator<T> focus() {
        this.node.requestFocus();
        return this;
    }

    public static <T extends TextField> TextFieldManipulator<T> create() {
        return new TextFieldManipulator<>();
    }

    public static <T extends TextField> TextFieldManipulator<T> of(final T textField) {
        return new TextFieldManipulator<>(textField);
    }

}
