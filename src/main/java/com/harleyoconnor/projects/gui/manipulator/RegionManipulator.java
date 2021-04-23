package com.harleyoconnor.projects.gui.manipulator;

import com.harleyoconnor.projects.Constants;
import com.harleyoconnor.projects.gui.util.InterfaceUtils;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;

/**
 * A helper class that helps easily manipulate {@link Region} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <R> The type of the {@link Region} being manipulated.
 * @param <RM> The type of the {@link RegionManipulator}.
 * @author Harley O'Connor
 */
@SuppressWarnings("unchecked") // Shh IDE. I know what I'm doing.
public class RegionManipulator<R extends Region, RM extends RegionManipulator<R, RM>> extends NodeManipulator<R, RM> {

    public RegionManipulator() {
        this((R) new Region());
    }

    public RegionManipulator(R region) {
        super(region);
    }

    /**
     * Applies the default amount of padding (15) to each side of the {@link Region}.
     *
     * @return This {@link Region} builder.
     */
    public RM padding() {
        return this.padding(Constants.DEFAULT_PADDING);
    }

    /**
     * Applies given amount of padding to each side of the {@link Region}.
     *
     * @param topRightBottomLeft The amount of padding to apply to the top, right, bottom, and left.
     * @return This {@link Region} builder.
     */
    public RM padding(double topRightBottomLeft) {
        return this.padding(topRightBottomLeft, topRightBottomLeft, topRightBottomLeft, topRightBottomLeft);
    }

    /**
     * Applies given amount of padding for each side to each side of the {@link Region}.
     *
     * @param top The amount of padding to apply to the top.
     * @param right The amount of padding to apply to the right.
     * @param bottom The amount of padding to apply to the bottom.
     * @param left The amount of padding to apply to the left.
     * @return This {@link Region} builder.
     */
    public RM padding(double top, double right, double bottom, double left) {
        this.node.setPadding(new Insets(top, right, bottom, left));
        return (RM) this;
    }

    /**
     * Sets the minimum width of the {@link Region} to the given width.
     *
     * @param width The width to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minWidth (double width) {
        this.node.setMinWidth(width);
        return (RM) this;
    }

    /**
     * Sets the minimum height of the {@link Region} to the given height.
     *
     * @param height The height to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minHeight (double height) {
        this.node.setMinHeight(height);
        return (RM) this;
    }

    /**
     * Sets both maximum width and height of the {@link Region} to the given value.
     *
     * @param widthHeight The height and width to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minWidthHeight (double widthHeight) {
        this.maxWidth(widthHeight);
        return this.maxHeight(widthHeight);
    }

    /**
     * Sets both minimum width and height of the {@link Region} to the minimum width and height given.
     *
     * @param width The width to set as a minimum.
     * @param height The height to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minWidthHeight (double width, double height) {
        this.maxWidth(width);
        return this.maxHeight(height);
    }

    /**
     * Sets the maximum width of the {@link Region} to the given width.
     *
     * @param width The width to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxWidth (double width) {
        this.node.setMaxWidth(width);
        return (RM) this;
    }

    /**
     * Sets the maximum height of the {@link Region} to the given height.
     *
     * @param height The height to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxHeight (double height) {
        this.node.setMaxHeight(height);
        return (RM) this;
    }

    /**
     * Sets both maximum width and height of the {@link Region} to the given value.
     *
     * @param widthHeight The height and width to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxWidthHeight (double widthHeight) {
        this.maxWidth(widthHeight);
        return this.maxHeight(widthHeight);
    }

    /**
     * Sets both maximum width and height of the {@link Region} to the maximum width and height given.
     *
     * @param width The width to set as a maximum.
     * @param height The height to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxWidthHeight (double width, double height) {
        this.maxWidth(width);
        return this.maxHeight(height);
    }

    /**
     * Fixes width of the {@link Region} by setting max and min width to width given.
     *
     * @param width The width to fix.
     * @return This {@link Region} builder.
     */
    public RM fixWidth (double width) {
        this.node.setMinWidth(width);
        this.node.setMaxWidth(width);
        return (RM) this;
    }

    /**
     * Fixes height of {@link Region} to height given.
     *
     * @param height The height to fix.
     * @return This {@link Region} builder.
     */
    public RM fixHeight (double height) {
        this.node.setMinHeight(height);
        this.node.setMaxHeight(height);
        return (RM) this;
    }

    /**
     * Fixes both width and height of the {@link Region} to given value.
     *
     * @param widthAndHeight The height and width to fix.
     * @return This {@link Region} builder.
     */
    public RM fixWidthHeight (double widthAndHeight) {
        return this.fixWidthHeight(widthAndHeight, widthAndHeight);
    }

    /**
     * Fixes both width and height of the {@link Region} to given width and height.
     *
     * @param width The width to fix.
     * @param height The height to fix.
     * @return This {@link Region} builder.
     */
    public RM fixWidthHeight (double width, double height) {
        this.fixWidth(width);
        return this.fixHeight(height);
    }

    public static <R extends Region, RM extends RegionManipulator<R, RM>> RegionManipulator<R, RM> horizontalSpacer() {
        return new RegionManipulator<>((R) InterfaceUtils.createHorizontalSpacer());
    }

    public static <R extends Region, RM extends RegionManipulator<R, RM>> RegionManipulator<R, RM> verticalSpacer() {
        return new RegionManipulator<>((R) InterfaceUtils.createVerticalSpacer());
    }

}
