package com.harleyoconnor.projects.gui.util;

import com.harleyoconnor.projects.Constants;
import com.harleyoconnor.projects.gui.builder.HBoxManipulator;
import com.harleyoconnor.projects.gui.builder.VBoxManipulator;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Harley O'Connor
 */
public final class InterfaceUtils {

    public static Region createHorizontalSpacer() {
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static Region createVerticalSpacer() {
        final Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    /**
     * Puts the given {@link Node} objects into a {@link HBox}, centring them horizontally and
     * applying the default spacing and padding.
     *
     * @param nodes The {@link Node} objects.
     * @return The {@link HBox} created.
     */
    public static HBox centreHorizontally(Node... nodes) {
        return centreHorizontally(Constants.DEFAULT_SPACING, nodes);
    }

    /**
     * Puts the given {@link Node} objects into a {@link VBox}, centring them vertically and
     * applying the default spacing and padding.
     *
     * @param nodes The {@link Node} objects.
     * @return The {@link VBox} created.
     */
    public static VBox centreVertically(Node... nodes) {
        return centreVertically(Constants.DEFAULT_SPACING, nodes);
    }

    /**
     * Puts the given {@link Node} objects into a {@link HBox}, centring them horizontally and
     * applying the given spacing and default padding.
     *
     * @param spacing The spacing to apply to the {@link HBox}.
     * @param nodes The {@link Node} objects.
     * @return The {@link HBox} created.
     */
    public static HBox centreHorizontally(int spacing, Node... nodes) {
        return centreHorizontally(Constants.DEFAULT_PADDING, spacing, nodes);
    }

    /**
     * Puts the given {@link Node} objects into a {@link VBox}, centring them vertically and
     * applying the given spacing and default padding.
     *
     * @param spacing The spacing to apply to the {@link VBox}.
     * @param nodes The {@link Node} objects.
     * @return The {@link VBox} created.
     */
    public static VBox centreVertically(int spacing, Node... nodes) {
        return centreVertically(Constants.DEFAULT_PADDING, spacing, nodes);
    }

    /**
     * Puts the given {@link Node} objects into a {@link HBox}, centring them horizontally and
     * applying the given spacing and padding.
     *
     * @param padding The padding to apply to the {@link HBox}.
     * @param spacing The spacing to apply to the {@link HBox}.
     * @param nodes The {@link Node} objects.
     * @return The {@link HBox} created.
     */
    public static HBox centreHorizontally(int padding, int spacing, Node... nodes) {
        return HBoxManipulator.create().add(nodes).padding(padding).spacing(spacing).centre().get();
    }

    /**
     * Puts the given {@link Node} objects into a {@link VBox}, centring them vertically and
     * applying the given spacing and padding.
     *
     * @param padding The padding to apply to the {@link VBox}.
     * @param spacing The spacing to apply to the {@link VBox}.
     * @param nodes The {@link Node} objects.
     * @return The {@link VBox} created.
     */
    public static VBox centreVertically(int padding, int spacing, Node... nodes) {
        return VBoxManipulator.create().add(nodes).padding(padding).spacing(spacing).centre().get();
    }

}
