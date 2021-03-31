package com.harleyoconnor.projects.gui.animation;

import com.harleyoconnor.projects.gui.manipulator.TimelineManipulator;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * Animation for handling slides across the screen.
 *
 * @author Harley O'Connor
 */
public class SlideAnimation<T extends Node> implements Animation {

    private final T node;
    private final TranslateAxis axis;
    private final Timeline animation;
    private final double endValue;
    private final int duration;
    private final Interpolator interpolator;

    public SlideAnimation(T node, TranslateAxis axis, double endValue, int duration) {
        this(node, axis, endValue, duration, Interpolator.LINEAR);
    }

    public SlideAnimation(T node, TranslateAxis axis, double endValue, int duration, Interpolator interpolator) {
        this.node = node;
        this.axis = axis;
        this.endValue = endValue;
        this.duration = duration;
        this.interpolator = interpolator;
        this.animation = this.createAnimation();
    }

    /**
     * Creates the animation {@link Timeline} object, setting the duration, axis, end value, and interpolator.
     *
     * @return The {@link Timeline} object created.
     */
    private Timeline createAnimation () {
        return TimelineManipulator.create().keyFrame(this.duration, this.axis.getTranslateProperty(this.node), this.endValue, this.interpolator).get();
    }

    @Override
    public Animation play() {
        this.animation.play();
        return this;
    }

    @Override
    public Animation stop() {
        this.animation.play();
        return this;
    }

    @Override
    public Animation setOnFinish(EventHandler<ActionEvent> eventHandler) {
        this.animation.setOnFinished(eventHandler);
        return this;
    }

    public TranslateAxis getAxis() {
        return axis;
    }

}
