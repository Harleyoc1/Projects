package com.harleyoconnor.projects.gui.manipulator;

import com.harleyoconnor.projects.Constants;
import javafx.scene.control.Label;

/**
 * A helper class that helps easily manipulate {@link Label} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <L> The type of the {@link Label} being manipulated.
 * @author Harley O'Connor
 */
public class LabelManipulator<L extends Label> extends RegionManipulator<L, LabelManipulator<L>> {

    @SuppressWarnings("unchecked")
    private LabelManipulator() {
        this((L) new Label());
    }

    private LabelManipulator(L label) {
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
        return new LabelManipulator<>().styleClasses(Constants.TEXT);
    }

    public static LabelManipulator<Label> create(final String text) {
        return create().text(text);
    }

    public static <L extends Label> LabelManipulator<L> of(final L label) {
        return new LabelManipulator<>(label);
    }

}
