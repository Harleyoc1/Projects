package com.harleyoconnor.projects.gui.builder;

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

    public static ImageViewManipulator<ImageView> create() {
        return new ImageViewManipulator<>(new ImageView());
    }

    public static ImageViewManipulator<ImageView> edit(ImageView imageView) {
        return new ImageViewManipulator<>(imageView);
    }

}
