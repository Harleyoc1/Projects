package com.harleyoconnor.projects.gui.builder;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * A helper class that helps easily manipulate {@link Label} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <L> The type of the {@link Label} being manipulated.
 * @author Harley O'Connor
 */
public class LabelManipulator<L extends Label> extends RegionManipulator<L, LabelManipulator<L>> {

    public LabelManipulator(L label) {
        super(label);
    }

    public LabelManipulator<L> text(String text) {
        this.node.setText(text);
        return this;
    }

    public LabelManipulator<L> wrapText () {
        this.node.setWrapText(true);
        return this;
    }

    public static LabelManipulator<Label> create() {
        return new LabelManipulator<>(new Label());
    }

}
