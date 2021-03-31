package com.harleyoconnor.projects.gui.manipulator;

import com.harleyoconnor.projects.gui.util.InterfaceUtils;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A helper class that helps easily manipulate {@link VBox} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <V> The type of the {@link VBox} being manipulated.
 * @author Harley O'Connor
 */
public class VBoxManipulator<V extends VBox> extends PaneManipulator<V, VBoxManipulator<V>> {

    @SuppressWarnings("unchecked")
    private VBoxManipulator() {
        this((V) new VBox());
    }

    private VBoxManipulator(V vBox) {
        super(vBox);
    }

    public VBoxManipulator<V> centre () {
        ObservableList<Node> nodeList = this.node.getChildren();
        nodeList.add(0, InterfaceUtils.createVerticalSpacer());
        nodeList.add(nodeList.size(), InterfaceUtils.createVerticalSpacer());
        return this;
    }

    public VBoxManipulator<V> spacing() {
        return this.spacing(5);
    }

    public VBoxManipulator<V> spacing(int amount) {
        this.node.setSpacing(amount);
        return this;
    }

    public static VBoxManipulator<VBox> create() {
        return new VBoxManipulator<>();
    }

    public static <V extends VBox> VBoxManipulator<V> of(final V vBox) {
        return new VBoxManipulator<>(vBox);
    }

}
