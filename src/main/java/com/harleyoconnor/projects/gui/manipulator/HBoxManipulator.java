package com.harleyoconnor.projects.gui.manipulator;

import com.harleyoconnor.projects.gui.util.InterfaceUtils;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * A helper class that helps easily manipulate {@link HBox} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <H> The type of the {@link HBox} being manipulated.
 * @author Harley O'Connor
 */
public class HBoxManipulator<H extends HBox> extends PaneManipulator<H, HBoxManipulator<H>> {

    @SuppressWarnings("unchecked")
    private HBoxManipulator() {
        this((H) new HBox());
    }

    private HBoxManipulator(H hBox) {
        super(hBox);
    }

    public HBoxManipulator<H> centre () {
        ObservableList<Node> nodeList = this.node.getChildren();
        nodeList.add(0, InterfaceUtils.createHorizontalSpacer());
        nodeList.add(nodeList.size(), InterfaceUtils.createHorizontalSpacer());
        return this;
    }

    public HBoxManipulator<H> spacing() {
        return this.spacing(5);
    }

    public HBoxManipulator<H> spacing(int amount) {
        this.node.setSpacing(amount);
        return this;
    }

    public static HBoxManipulator<HBox> create() {
        return new HBoxManipulator<>(new HBox());
    }

    public static <H extends HBox> HBoxManipulator<H> of(final H hBox) {
        return new HBoxManipulator<>(hBox);
    }

}
