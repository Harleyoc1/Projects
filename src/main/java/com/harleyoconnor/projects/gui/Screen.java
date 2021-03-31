package com.harleyoconnor.projects.gui;

import com.harleyoconnor.projects.gui.animation.SlideAnimation;
import com.harleyoconnor.projects.gui.animation.TranslateAxis;
import com.harleyoconnor.projects.gui.manipulator.PaneManipulator;
import com.harleyoconnor.projects.gui.manipulator.SceneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StageManipulator;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class holds common capabilities necessary for different {@link #layout}s, such as
 * {@link #toNewScreen(Screen)} to smoothly transition to new screens.
 *
 * @param <P> The type of the {@link Pane} layout.
 * @author Harley O'Connor
 */
public abstract class Screen<P extends Pane, PM extends PaneManipulator<P, PM>> {

    protected final StageManipulator<Stage> stage;
    protected final SceneManipulator<Scene> scene;
    protected final PaneManipulator<Pane, ?> parentView;
    protected final Screen<?, ?> previousScreen;

    protected PM layout;
    private String title;

    private boolean inFocus;

    public Screen(final StageManipulator<Stage> stage, final SceneManipulator<Scene> scene, final PaneManipulator<Pane, ?> parentView, final Screen<?, ?> previousScreen, final String initialTitle) {
        this.stage = stage;
        this.scene = scene;
        this.parentView = parentView;
        this.previousScreen = previousScreen;

        this.setAndInitialiseLayout();
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
            this.stage.title(newTitle);
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
    public void toNewScreen(final Screen<?, ?> newScreen) {
        this.toNewScreen(newScreen, TranslateAxis.X);
    }

    /**
     * Transitions to given {@link Screen} with a {@link SlideAnimation} over the given {@link TranslateAxis}
     * from the {@code negative} direction.
     *
     * @param newScreen The new {@link Screen} to display.
     * @param slideAxis The {@link TranslateAxis} to slide on.
     */
    public void toNewScreen(final Screen<?, ?> newScreen, final TranslateAxis slideAxis) {
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
    public void toNewScreen (final Screen<?, ?> newScreen, final TranslateAxis slideAxis, final boolean slideFromPositive) {
        // If the main layout in the new screen isn't initialised, initialise it.
        if (newScreen.layout == null)
            newScreen.setAndInitialiseLayout();

        // Offset the new layout.
        slideAxis.getTranslateProperty(newScreen.layout.get()).setValue(slideAxis == TranslateAxis.X ? (slideFromPositive ? 1 : -1 ) * this.scene.get().getWidth() : (slideFromPositive ? 1 : -1) * this.scene.get().getHeight());

        newScreen.show();

        // Make the animation so it slides over this layout.
        new SlideAnimation<>(newScreen.layout.get(), slideAxis, 0, 1000, Interpolator.EASE_BOTH).setOnFinish(event -> this.onSlideOutFinished(event, newScreen)).play();
    }

    /**
     * Initialises and performs initial setup for a {@link P} layout
     * for this {@link Screen}.
     *
     * <p>Subclasses should note that this is called from
     * {@link #Screen(StageManipulator, SceneManipulator, PaneManipulator, Screen, String)}
     * and therefore the subclass's fields won't have been initialised yet. Additional
     * layout setup should be done in the subclass's constructor.</p>
     *
     * @return The constructed {@link P} layout.
     */
    protected abstract PM initialiseLayout();

    /**
     * Sets {@link #layout} to the result of calling {@link #initialiseLayout()}.
     */
    private void setAndInitialiseLayout() {
        this.layout = this.initialiseLayout();
    }


    public final void show () {
        this.parentView.add(this.layout);

        // Run custom actions for extra screen setup.
        this.onShow();
    }

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
        this.stage.title(this.title);
    }

    /**
     * Executes when the {@link SlideAnimation} is finished sliding this screen out.
     *
     * @param event The {@link ActionEvent}.
     * @param newScreen The new {@link Screen} now being displayed.
     */
    protected void onSlideOutFinished (final ActionEvent event, final Screen<?, ?> newScreen) {
        this.parentView.remove(this.layout);
        this.inFocus = false;
        newScreen.onSlideInFinished(event);
    }

}
