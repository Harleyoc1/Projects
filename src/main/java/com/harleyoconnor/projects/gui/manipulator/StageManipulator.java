package com.harleyoconnor.projects.gui.manipulator;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

/**
 * @author Harley O'Connor
 */
public class StageManipulator<S extends Stage> implements ChainManipulator<S> {

    private final S stage;

    @SuppressWarnings("unchecked")
    private StageManipulator() {
        this((S) new Stage());
    }

    private StageManipulator(S stage) {
        this.stage = stage;
    }

    public StageManipulator<S> title(final String title) {
        this.stage.setTitle(title);
        return this;
    }

    public StageManipulator<S> minWidth(final double width) {
        this.stage.setMinWidth(width);
        return this;
    }

    public StageManipulator<S> minHeight(final double height) {
        this.stage.setMinHeight(height);
        return this;
    }

    public StageManipulator<S> width(final double width) {
        this.stage.setWidth(width);
        return this;
    }

    public StageManipulator<S> height(final double height) {
        this.stage.setHeight(height);
        return this;
    }

    public StageManipulator<S> scene(final Scene scene) {
        this.stage.setScene(scene);
        return this;
    }

    public StageManipulator<S> show() {
        this.stage.show();
        return this;
    }

    public StageManipulator<S> hide() {
        this.stage.hide();
        return this;
    }

    @Nonnull
    @Override
    public S get() {
        return this.stage;
    }

    public static StageManipulator<Stage> create() {
        return new StageManipulator<>();
    }

    public static <S extends Stage> StageManipulator<S> of(final S stage) {
        return new StageManipulator<>(stage);
    }

}
