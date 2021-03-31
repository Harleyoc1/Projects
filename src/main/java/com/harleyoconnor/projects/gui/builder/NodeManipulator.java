package com.harleyoconnor.projects.gui.builder;

import com.harleyoconnor.projects.Constants;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;

import javax.annotation.Nonnull;

/**
 * An abstract class for a simple JavaFX node builder.
 *
 * @author Harley O'Connor
 */
@SuppressWarnings("unchecked") // Shh IDE. I know what I'm doing.
public abstract class NodeManipulator<N extends Node, NM extends NodeManipulator<N, NM>> implements ChainManipulator<N> {

    protected final N node;

    public NodeManipulator(N node) {
        this.node = node;
    }

    /**
     * Adds style classes to the {@link Node}.
     *
     * @param styleClassIds The style classes to add.
     * @return This {@link Node} builder.
     */
    public NM styleClasses(String... styleClassIds) {
        this.node.getStyleClass().addAll(styleClassIds);
        return (NM) this;
    }

    /**
     * Adds title style class to the {@link Node}.
     *
     * @return This {@link Node} builder.
     */
    public NM title() {
        return this.styleClasses(Constants.TITLE);
    }

    /**
     * Adds body style class to the {@link Node}.
     *
     * @return This {@link Node} builder.
     */
    public NM body() {
        return this.styleClasses(Constants.BODY);
    }

    /**
     * Translates the {@link Node} object's x-axis by the given amount.
     *
     * @param amount The amount to translate it.
     * @return This {@link Node} builder.
     */
    public NM translateX (double amount) {
        return this.translate('X', amount);
    }

    /**
     * Translates the {@link Node} object's y-axis by the given amount.
     *
     * @param amount The amount to translate it.
     * @return This {@link Node} builder.
     */
    public NM translateY (double amount) {
        return this.translate('Y', amount);
    }

    /**
     * Translates the {@link Node} object's z-axis by the given amount.
     *
     * @param amount The amount to translate it.
     * @return This {@link Node} builder.
     */
    public NM translateZ (double amount) {
        return this.translate('Z', amount);
    }

    /**
     * Translates the {@link Node} object's given axis by the given amount.
     *
     * @param xyz The axis translate.
     * @param amount The amount to translate it.
     * @return This {@link Node} builder.
     */
    private NM translate(char xyz, double amount) {
        this.getTranslate(xyz).set(amount);
        return (NM) this;
    }

    /**
     * Gets the {@link DoubleProperty} for the given axis.
     *
     * @param xyz The axis to get the {@link DoubleProperty} for.
     * @return The {@link DoubleProperty} for the given axis.
     */
    private DoubleProperty getTranslate (char xyz) {
        return switch (xyz) {
            case 'X' -> this.node.translateXProperty();
            case 'Y' -> this.node.translateYProperty();
            default -> this.node.translateZProperty();
        };
    }

    /**
     * @return The {@link Node} that's been configured.
     */
    @Nonnull
    @Override
    public N get() {
        return this.node;
    }

}
