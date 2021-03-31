package com.harleyoconnor.projects.gui;

import com.harleyoconnor.projects.gui.animation.SlideAnimation;
import com.harleyoconnor.projects.gui.animation.TranslateAxis;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class holds common capabilities necessary for different {@link #layout}s, such as
 * {@link #toNewScreen(Screen)} to smoothly transition to new screens.
 *
 * @author Harley O'Connor
 */
public abstract class Screen {

    protected final Stage stage;
    protected final Scene scene;
    protected final Pane parentView;
    protected final Screen previousScreen;

    protected Pane layout;
    private String title;

    private boolean inFocus;

    public Screen(final Stage stage, final Scene scene, final Pane parentView, final Screen previousScreen, final String initialTitle) {
        this.stage = stage;
        this.scene = scene;
        this.parentView = parentView;
        this.previousScreen = previousScreen;
        this.layout = this.initialiseLayout();
        this.title = initialTitle;
    }

    /**
     * Updates the {@link #title} of this {@link Screen}. Note that it also updates the title
     * on the {@link #stage} if this {@link Screen} is currently {@link #inFocus}.
     *
     * @param newTitle The updated {@link #title}.
     */
    public void updateTitle (final String newTitle) {
        this.title = newTitle;

        if (this.inFocus)
            this.stage.setTitle(newTitle);
    }

    /**
     * Checks if this {@link Screen} is currently in focus. The default for implementations is for
     * the {@link Screen} object to set {@link #inFocus} to {@code true} in
     * {@link #onSlideInFinished(ActionEvent)} and for it to reset it to {@code false} in
     * {@link #onSlideOutFinished(ActionEvent, Screen)}.
     *
     * @return {@code true} if this {@link Screen} is currently in focus; false otherwise.
     */
    public boolean currentlyInFocus() {
        return this.inFocus;
    }

    /**
     * Transitions to given {@link Screen} with a {@link SlideAnimation} over the x-axis from the
     * {@code negative} direction.
     *
     * @param newScreen The new {@link Screen} to display.
     */
    public void toNewScreen(final Screen newScreen) {
        this.toNewScreen(newScreen, TranslateAxis.X);
    }

    /**
     * Transitions to given {@link Screen} with a {@link SlideAnimation} over the given {@link TranslateAxis}
     * from the {@code negative} direction.
     *
     * @param newScreen The new {@link Screen} to display.
     * @param slideAxis The {@link TranslateAxis} to slide on.
     */
    public void toNewScreen(final Screen newScreen, final TranslateAxis slideAxis) {
        this.toNewScreen(newScreen, TranslateAxis.X, false);
    }

    /**
     * Transitions to given {@link Screen} with a {@link SlideAnimation} over the given {@link TranslateAxis}
     * from positive or negative direction.
     *
     * @param newScreen The new {@link Screen} to display.
     * @param slideAxis The {@link TranslateAxis} to slide on.
     * @param slideFromPositive True if it should slide from the positive direction.
     */
    public void toNewScreen (final Screen newScreen, final TranslateAxis slideAxis, final boolean slideFromPositive) {
        // If the main layout in the new screen isn't initialised, initialise it.
        if (newScreen.layout == null)
            newScreen.layout = newScreen.initialiseLayout();

        // Offset the new layout.
        slideAxis.getTranslateProperty(newScreen.layout).setValue(slideAxis == TranslateAxis.X ? (slideFromPositive ? 1 : -1 ) * this.scene.getWidth() : (slideFromPositive ? 1 : -1) * this.scene.getHeight());

        // Run custom actions for extra screen setup.
        newScreen.onShow();

        // Make the animation so it slides over this layout.
        new SlideAnimation<>(newScreen.layout, slideAxis, 0, 1000, Interpolator.EASE_BOTH).setOnFinish(event -> this.onSlideOutFinished(event, newScreen)).play();
    }

    /**
     * Initialises and performs initial setup for a {@link Pane} layout
     * for this {@link Screen}.
     *
     * @return The constructed {@link Pane} layout.
     */
    protected abstract Pane initialiseLayout();

    /**
     * Executes when {@link #toNewScreen(Screen, TranslateAxis, boolean)} is called for
     * additional setup before the slide animation starts.
     */
    protected void onShow() {}

    /**
     * Executes when the {@link SlideAnimation} has finished sliding this screen in
     * allowing for additional actions to be performed once the screen is in full
     * focus.
     *
     * @param event The {@link ActionEvent}.
     */
    protected void onSlideInFinished (final ActionEvent event) {
        this.inFocus = true;

        // Update the title of the Stage to the current title set.
        this.stage.setTitle(this.title);
    }

    /**
     * Executes when the {@link SlideAnimation} is finished sliding this screen out.
     *
     * @param event The {@link ActionEvent}.
     * @param newScreen The new {@link Screen} now being displayed.
     */
    protected void onSlideOutFinished (final ActionEvent event, final Screen newScreen) {
        this.parentView.getChildren().remove(this.layout);
        this.inFocus = false;
        newScreen.onSlideInFinished(event);
    }

}
