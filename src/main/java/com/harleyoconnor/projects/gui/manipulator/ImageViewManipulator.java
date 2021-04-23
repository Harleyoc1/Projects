package com.harleyoconnor.projects.gui.manipulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A helper class that helps easily manipulate {@link ImageView} {@link Object}s by
 * chaining configuration of its properties.
 *
 * @param <I> The type of the {@link ImageView} being manipulated.
 * @author Harley O'Connor
 */
public class ImageViewManipulator<I extends ImageView> extends NodeManipulator<I, ImageViewManipulator<I>> {

    public ImageViewManipulator(I imageView) {
        super(imageView);
    }

    public ImageViewManipulator<I> image(Image image) {
        this.node.setImage(image);
        return this;
    }

    public ImageViewManipulator<I> width(int width) {
        this.node.setFitWidth(width);
        return this;
    }

    public ImageViewManipulator<I> height(int height) {
        this.node.setFitHeight(height);
        return this;
    }

    public ImageViewManipulator<I> preserveRatio() {
        this.node.setPreserveRatio(true);
        return this;
    }

    public ImageViewManipulator<I> smooth() {
        this.node.setSmooth(true);
        return this;
    }

    public ImageViewManipulator<I> cache() {
        this.node.setCache(true);
        return this;
    }

    public static ImageViewManipulator<ImageView> create() {
        return new ImageViewManipulator<>(new ImageView());
    }

    public static <I extends ImageView> ImageViewManipulator<I> edit(final I imageView) {
        return new ImageViewManipulator<>(imageView);
    }

}
