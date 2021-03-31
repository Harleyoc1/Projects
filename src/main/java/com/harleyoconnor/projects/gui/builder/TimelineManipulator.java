package com.harleyoconnor.projects.gui.builder;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javax.annotation.Nonnull;

/**
 * A helper class that helps easily manipulate {@link Timeline} {@link Object}s by
 * chaining configuration of its properties.
 * 
 * @author Harley O'Connor
 */
public final class TimelineManipulator implements ChainManipulator<Timeline> {

    private final Timeline timeline;

    public TimelineManipulator(Timeline timeline) {
        this.timeline = timeline;
    }

    public <V> TimelineManipulator keyFrame (int millis, WritableValue<V> value, V endValue) {
        return this.keyFrame(millis, value, endValue, Interpolator.LINEAR);
    }

    public <V> TimelineManipulator keyFrame (int millis, WritableValue<V> value, V endValue, Interpolator interpolator) {
        return this.keyFrame(Duration.millis(millis), value, endValue, interpolator);
    }

    public <V> TimelineManipulator keyFrame (Duration duration, WritableValue<V> value, V endValue, Interpolator interpolator) {
        this.timeline.getKeyFrames().add(new KeyFrame(duration, new KeyValue(value, endValue, interpolator)));
        return this;
    }

    public TimelineManipulator keyFrame (int millis, EventHandler<ActionEvent> eventHandler) {
        return this.keyFrame(Duration.millis(millis), eventHandler);
    }

    public TimelineManipulator keyFrame (Duration duration, EventHandler<ActionEvent> eventHandler) {
        this.timeline.getKeyFrames().add(new KeyFrame(duration, eventHandler));
        return this;
    }

    public TimelineManipulator keyFrame (KeyFrame keyFrame) {
        this.timeline.getKeyFrames().add(keyFrame);
        return this;
    }

    public TimelineManipulator onFinished (EventHandler<ActionEvent> eventHandler) {
        this.timeline.setOnFinished(eventHandler);
        return this;
    }

    @Nonnull
    @Override
    public Timeline get() {
        return this.timeline;
    }

    public static TimelineManipulator create () {
        return new TimelineManipulator(new Timeline());
    }

}
