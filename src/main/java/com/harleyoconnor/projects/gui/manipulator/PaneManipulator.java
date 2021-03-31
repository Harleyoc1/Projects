package com.harleyoconnor.projects.gui.manipulator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Arrays;
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
     * Adds the given {@link Node} {@link Object}s in the {@link NodeManipulator}s
     * given to the {@link Pane}.
     *
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder.
     */
    public PM add (NodeManipulator<?, ?>... nodeManipulators) {
        this.node.getChildren().addAll(Stream.of(nodeManipulators).map(NodeManipulator::get).toArray(Node[]::new));
        return (PM) this;
    }

    /**
     * Adds the given {@link Node} objects to the {@link Pane}.
     *
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder.
     */
    public PM add (Node... nodes) {
        this.node.getChildren().addAll(nodes);
        return (PM) this;
    }

    /**
     * Adds each given {@link Node} object to the {@link Pane}, if it's not already present.
     *
     * @param nodes The {@link Node} objects to add.
     * @return This {@link Pane} builder.
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
     * @return This {@link Pane} builder.
     */
    public PM insert (Node node, int index) {
        this.node.getChildren().add(index, node);
        return (PM) this;
    }

    /**
     * Adds the given {@link Node} object to the {@link Pane} at the given index, if not already present.
     *
     * @param node The {@link Node} object to add.
     * @param index The index to insert to.
     * @return This {@link Pane} builder.
     */
    public PM insertIfNotPresent (Node node, int index) {
        return this.node.getChildren().contains(node) ? (PM) this : this.insert(node, index);
    }

    /**
     * Removes the given {@link Node} {@link Object}s from the {@link Pane}.
     *
     * @param nodes The {@link Node} objects to remove.
     * @return This {@link Pane} builder.
     */
    public PM remove (Node... nodes) {
        this.node.getChildren().removeAll(nodes);
        return (PM) this;
    }

    /**
     * Removes the given {@link Node} {@link Object}s in the {@link NodeManipulator}s
     * given to the {@link Pane}.
     *
     * @param nodeManipulators The {@link NodeManipulator} objects to add.
     * @return This {@link Pane} builder.
     */
    public PM remove (NodeManipulator<?, ?>... nodeManipulators) {
        this.node.getChildren().removeAll(Stream.of(nodeManipulators).map(NodeManipulator::get).toArray(Node[]::new));
        return (PM) this;
    }

}
