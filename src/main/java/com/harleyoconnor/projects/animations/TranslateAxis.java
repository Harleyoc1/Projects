package com.harleyoconnor.projects.animations;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;

/**
 * Holds the axis of a translation.
 *
 * @author Harley O'Connor
 */
public enum TranslateAxis {
    X, Y, Z;

    /**
     * Gets the {@link DoubleProperty} for the translation for the current Axis object.
     *
     * @param node The {@link Node} object.
     * @return The {@link DoubleProperty} for the translation for the current Axis object.
     */
    public DoubleProperty getTranslateProperty(Node node) {
        return switch (this) {
            case X -> node.translateXProperty();
            case Y -> node.translateYProperty();
            case Z -> node.translateZProperty();
        };
    }
}
