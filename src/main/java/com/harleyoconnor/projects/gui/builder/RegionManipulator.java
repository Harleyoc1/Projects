package com.harleyoconnor.projects.gui.builder;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;

/**
 * @author Harley O'Connor
 */
@SuppressWarnings("unchecked") // Shh IDE. I know what I'm doing.
public abstract class RegionManipulator<R extends Region, RM extends RegionManipulator<R, RM>> extends NodeManipulator<R, RM> {

    public RegionManipulator(R region) {
        super(region);
    }

    /**
     * Applies the default amount of padding (15) to each side of the {@link Region}.
     *
     * @return This {@link Region} builder.
     */
    public RM padding() {
        return this.padding(15);
    }

    /**
     * Applies given amount of padding to each side of the {@link Region}.
     *
     * @param topRightBottomLeft The amount of padding to apply to the top, right, bottom, and left.
     * @return This {@link Region} builder.
     */
    public RM padding(int topRightBottomLeft) {
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
    public RM padding(int top, int right, int bottom, int left) {
        this.node.setPadding(new Insets(top, right, bottom, left));
        return (RM) this;
    }

    /**
     * Sets the minimum width of the {@link Region} to the given width.
     *
     * @param width The width to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minWidth (int width) {
        this.node.setMinWidth(width);
        return (RM) this;
    }

    /**
     * Sets the minimum height of the {@link Region} to the given height.
     *
     * @param height The height to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minHeight (int height) {
        this.node.setMinHeight(height);
        return (RM) this;
    }

    /**
     * Sets both maximum width and height of the {@link Region} to the given value.
     *
     * @param widthHeight The height and width to set as a minimum.
     * @return This {@link Region} builder.
     */
    public RM minWidthHeight (int widthHeight) {
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
    public RM minWidthHeight (int width, int height) {
        this.maxWidth(width);
        return this.maxHeight(height);
    }

    /**
     * Sets the maximum width of the {@link Region} to the given width.
     *
     * @param width The width to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxWidth (int width) {
        this.node.setMaxWidth(width);
        return (RM) this;
    }

    /**
     * Sets the maximum height of the {@link Region} to the given height.
     *
     * @param height The height to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxHeight (int height) {
        this.node.setMaxHeight(height);
        return (RM) this;
    }

    /**
     * Sets both maximum width and height of the {@link Region} to the given value.
     *
     * @param widthHeight The height and width to set as a maximum.
     * @return This {@link Region} builder.
     */
    public RM maxWidthHeight (int widthHeight) {
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
    public RM maxWidthHeight (int width, int height) {
        this.maxWidth(width);
        return this.maxHeight(height);
    }

    /**
     * Fixes width of the {@link Region} by setting max and min width to width given.
     *
     * @param width The width to fix.
     * @return This {@link Region} builder.
     */
    public RM fixWidth (int width) {
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
    public RM fixHeight (int height) {
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
    public RM fixWidthHeight (int widthAndHeight) {
        return this.fixWidthHeight(widthAndHeight, widthAndHeight);
    }

    /**
     * Fixes both width and height of the {@link Region} to given width and height.
     *
     * @param width The width to fix.
     * @param height The height to fix.
     * @return This {@link Region} builder.
     */
    public RM fixWidthHeight (int width, int height) {
        this.fixWidth(width);
        return this.fixHeight(height);
    }

}