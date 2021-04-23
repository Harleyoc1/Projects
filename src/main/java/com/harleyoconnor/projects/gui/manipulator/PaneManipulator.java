package com.harleyoconnor.projects.gui.manipulator;

import com.harleyoconnor.projects.Constants;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.stream.Stream;

/**
 * A helper class that helps easily manipulate {@link Pane} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <P> The type of the {@link Pane} being manipulated.
 * @param <PM> The type of the {@link PaneManipulator}.
 * @author Harley O'Connor
 */
@SuppressWarnings("unchecked") // Shh IDE. I know what I'm doing.
public class PaneManipulator<P extends Pane, PM extends PaneManipulator<P, PM>> extends RegionManipulator<P, PM> {

    public PaneManipulator() {
        this((P) new Pane());
    }

    public PaneManipulator(P pane) {
        super(pane);
    }

    /**
     * Adds the {@link Node} {@link Object}s in the specified {@link NodeManipulator}s
     * to the {@link Pane}.
     *
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM add (NodeManipulator<?, ?>... nodeManipulators) {
        this.node.getChildren().addAll(Stream.of(nodeManipulators).map(NodeManipulator::get).toArray(Node[]::new));
        return (PM) this;
    }

    /**
     * Adds the given {@link Node} objects to the {@link Pane}.
     *
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM add (Node... nodes) {
        this.node.getChildren().addAll(nodes);
        return (PM) this;
    }

    /**
     * Adds each given {@link Node} object to the {@link Pane}, if it's not already present.
     *
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addIfNotPresent (Node... nodes) {
        for (Node node : nodes) {
            if (!this.node.getChildren().contains(node))
                this.add(node);
        }
        return (PM) this;
    }

    /**
     * Adds the given {@link Node} object to the {@link Pane} at the given index.
     *
     * @param node The {@link Node} object to add.
     * @param index The index to insert to.
     * @return This {@link Pane} builder for chaining.
     */
    public PM insert (Node node, int index) {
        this.node.getChildren().add(index, node);
        return (PM) this;
    }

    /**
     * Adds the given {@link Node} object to the {@link Pane} at the given index, if
     * not already present.
     *
     * @param node The {@link Node} object to add.
     * @param index The index to insert to.
     * @return This {@link Pane} builder for chaining.
     */
    public PM insertIfNotPresent (Node node, int index) {
        return this.node.getChildren().contains(node) ? (PM) this : this.insert(node, index);
    }

    /**
     * Removes the given {@link Node} {@link Object}s from the {@link Pane}.
     *
     * @param nodes The {@link Node} objects to remove.
     * @return This {@link Pane} builder for chaining.
     */
    public PM remove (Node... nodes) {
        this.node.getChildren().removeAll(nodes);
        return (PM) this;
    }

    /**
     * Removes the {@link Node} {@link Object}s in the specified {@link NodeManipulator}s
     * to the {@link Pane}.
     *
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM remove (NodeManipulator<?, ?>... nodeManipulators) {
        this.node.getChildren().removeAll(Stream.of(nodeManipulators).map(NodeManipulator::get).toArray(Node[]::new));
        return (PM) this;
    }

    /**
     * Clears the {@link Pane}'s children {@link Node}s.
     *
     * @return This {@link Pane} builder for chaining.
     */
    public PM clear() {
        this.node.getChildren().clear();
        return (PM) this;
    }

    /**
     * Clears all of the {@link Pane}'s children {@link Node}s that are an instance
     * of the given {@code nodeClass}.
     *
     * @param nodeClass The node {@link Class} to clear.
     * @return This {@link Pane} builder for chaining.
     */
    public PM clear(final Class<? extends Node> nodeClass) {
        this.node.getChildren().removeIf(nodeClass::isInstance);
        return (PM) this;
    }

    /**
     * Clears all of the {@link Pane}'s children {@link Node}s that have a style class
     * of the specified name.
     *
     * @param styleClass The style class to clear.
     * @return This {@link Pane} builder for chaining.
     */
    public PM clear(final String styleClass) {
        this.node.getChildren().removeIf(node -> node.getStyleClass().contains(styleClass));
        return (PM) this;
    }

    /**
     * Adds the {@link Node} {@link Object}s in the specified {@link NodeManipulator}s
     * to the {@link VBox}, adding the default amount of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addWithMargin (final NodeManipulator<?, ?>... nodeManipulators) {
        final double margin = Constants.DEFAULT_MARGIN;
        return this.addWithMargin(margin, margin, margin, margin, nodeManipulators);
    }

    /**
     * Adds the given {@link Node} objects to the {@link Pane}, adding the specified
     * default of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addWithMargin (final Node... nodes) {
        final double margin = Constants.DEFAULT_MARGIN;
        return this.addWithMargin(margin, margin, margin, margin, nodes);
    }

    /**
     * Adds the {@link Node} {@link Object}s in the specified {@link NodeManipulator}s
     * to the {@link VBox}, adding the specified amount of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param margin The amount of margin to apply to all sides.
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addWithMargin (final double margin, final NodeManipulator<?, ?>... nodeManipulators) {
        return this.addWithMargin(margin, margin, margin, margin, nodeManipulators);
    }

    /**
     * Adds the given {@link Node} objects to the {@link Pane}, adding the specified
     * amount of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param margin The amount of margin to apply to all sides.
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addWithMargin (final double margin, final Node... nodes) {
        return this.addWithMargin(margin, margin, margin, margin, nodes);
    }

    /**
     * Adds the {@link Node} {@link Object}s in the specified {@link NodeManipulator}s
     * to the {@link VBox}, adding the specified amount of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param top The amount of margin to apply to the top.
     * @param right The amount of margin to apply to the right.
     * @param bottom The amount of margin to apply to the bottom.
     * @param left The amount of margin to apply to the left.
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder for chaining.
     */
    public PM addWithMargin (final double top, final double right,
                                             final double bottom, final double left,
                                             final NodeManipulator<?, ?>... nodeManipulators) {
        return this.addWithMargin(top, right, bottom, left,
                Stream.of(nodeManipulators).map(NodeManipulator::get).toArray(Node[]::new));
    }

    /**
     * Adds the given {@link Node} objects to the {@link Pane}, adding the specified
     * amount of margin to each.
     *
     * <p>Note that certain implementations will not support this method. Check the
     * {@link PaneManipulator} has a functional implementation of
     * {@link #setMargin(Node, Insets)} before using.</p>
     *
     * @param top The amount of margin to apply to the top.
     * @param right The amount of margin to apply to the right.
     * @param bottom The amount of margin to apply to the bottom.
     * @param left The amount of margin to apply to the left.
     * @param nodes The {@link Node} objects to add.
     * @return This {@link PM} for chaining.
     */
    public PM addWithMargin (final double top, final double right, final double bottom,
                                             final double left, final Node... nodes) {
        final Insets insets = new Insets(top, right, bottom, left);
        Stream.of(nodes).forEach(node -> {
            this.setMargin(node, insets);
            this.node.getChildren().add(node);
        });
        return (PM) this;
    }

    /**
     * Sets the margin for the specified {@link Node} to be nested in this
     * {@link Pane}. Default implementation does nothing as it is not supported
     * for panes.
     *
     * @param node The {@link Node} to set the margin for.
     * @param insets The {@link Insets} to set as the margin.
     */
    protected void setMargin(final Node node, final Insets insets) { }

    public static <P extends Pane, PM extends PaneManipulator<P, PM>> PaneManipulator<P, PM> pane() {
        return new PaneManipulator<>();
    }

}
