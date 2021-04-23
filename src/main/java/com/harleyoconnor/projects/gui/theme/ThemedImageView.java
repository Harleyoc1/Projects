package com.harleyoconnor.projects.gui.theme;

import com.harleyoconnor.javautilities.util.FileUtils;
import com.harleyoconnor.projects.Constants;
import com.harleyoconnor.projects.gui.manipulator.ImageViewManipulator;
import com.harleyoconnor.projects.os.SystemManager;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an {@link ImageView} that has separate {@link Image}s for
 * light and dark mode.
 *
 * @author Harley O'Connor
 */
public final class ThemedImageView implements Themed {

    private final ImageViewManipulator<ImageView> manipulator;

    private final Map<SystemManager.Theme, Image> themeImageMap = new HashMap<>();

    public ThemedImageView(final String path) {
        this(path.substring(0, path.lastIndexOf('.')), path.substring(path.lastIndexOf('.')));
    }

    public ThemedImageView(final String path, final String extension) {
        this(path, extension, "-light", "-dark");
    }

    public ThemedImageView(final String path, final String extension, final String lightSuffix, final String darkSuffix) {
        this(ImageViewManipulator.create());

        this.themeImageMap.put(SystemManager.Theme.LIGHT, new Image(Constants.FILE + FileUtils.getInternalPath(path + lightSuffix + extension)));
        this.themeImageMap.put(SystemManager.Theme.DARK, new Image(Constants.FILE + FileUtils.getInternalPath(path + darkSuffix + extension)));

        // Default to light theme image.
        this.manipulator.image(this.themeImageMap.get(SystemManager.Theme.LIGHT));

        // Add as Themed so that #themeChange is called.
        ThemedManager.INSTANCE.addThemed(this);
    }

    public ThemedImageView(ImageViewManipulator<ImageView> manipulator) {
        this.manipulator = manipulator;
    }

    @Override
    public void themeChange(SystemManager.Theme oldTheme, SystemManager.Theme newTheme) {
        this.manipulator.image(this.themeImageMap.get(newTheme));
    }

    /**
     * Gets the {@link #manipulator} for this {@link ThemedImageView} object.
     *
     * @return The {@link #manipulator} for this {@link ThemedImageView} object.
     */
    public ImageViewManipulator<ImageView> getManipulator() {
        return this.manipulator;
    }

}
