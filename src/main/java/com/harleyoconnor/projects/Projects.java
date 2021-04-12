package com.harleyoconnor.projects;

import com.harleyoconnor.projects.gui.SignInScreen;
import com.harleyoconnor.projects.gui.manipulator.SceneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StackPaneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StageManipulator;
import com.harleyoconnor.projects.gui.stylesheets.StylesheetManager;
import com.harleyoconnor.projects.gui.stylesheets.ThemedStylesheet;
import com.harleyoconnor.projects.object.Employee;
import com.harleyoconnor.projects.util.Injected;
import com.harleyoconnor.projects.util.ReflectionHelper;
import com.harleyoconnor.projects.util.Scheduler;
import com.harleyoconnor.serdes.database.Database;
import com.harleyoconnor.serdes.util.SQLHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * The main {@link Application} class.
 *
 * @author Harley O'Connor
 */
public final class Projects extends Application {

    /**
     * The {@link Database} instance.
     */
    private static Database DATABASE;

    @Injected
    public static final Projects INSTANCE = new Projects(null);

    public static final StylesheetManager STYLESHEET_MANAGER = new StylesheetManager();

    private final StackPaneManipulator<StackPane> primaryView = StackPaneManipulator.create();
    private final SceneManipulator<Scene> primaryScene = SceneManipulator.create(this.primaryView.get());

    @Injected
    private final StageManipulator<Stage> primaryStage = StageManipulator.of(null);

    private Employee currentEmployee;

    @SuppressWarnings("unused")
    public Projects() {
        ReflectionHelper.setFieldUnchecked(this.getClass(), "INSTANCE", this);
    }

    private Projects(@SuppressWarnings("unused") @Nullable Object placeholder) {}

    @Override
    public void start(final Stage primaryStage) {
        ReflectionHelper.setFieldUnchecked(this, "primaryStage", StageManipulator.of(primaryStage));

        this.primaryStage.minWidth(Constants.MIN_WIDTH).minHeight(Constants.MIN_HEIGHT)
                .width(Constants.DEFAULT_WIDTH).height(Constants.DEFAULT_HEIGHT).title("Projects")
                .scene(this.primaryScene.get()).show();

        // Add the default stylesheet.
        STYLESHEET_MANAGER.addStylesheet(this.primaryScene.get().getStylesheets(), new ThemedStylesheet("default"));

        // Reset database connection every 15 minutes (this may move to SerDes at some point).
        Scheduler.schedule(this::resetDatabase, Duration.ofMinutes(15));

        // Create and show the sign in screen.
        new SignInScreen(this.primaryStage, this.primaryScene, this.primaryView.toPaneManipulator(), null).show();
    }

    /**
     * Resets the {@link #DATABASE} connection, since if left for a while the connection can drop.
     */
    private void resetDatabase () {
        DATABASE = new Database(SQLHelper.getConnectionUnsafe("mariadb", DatabaseConstants.IP, DatabaseConstants.PORT,
                DatabaseConstants.SCHEMA, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD));
    }

    /**
     * Gets the {@link #currentEmployee} for this {@link Projects} object.
     *
     * @return The {@link #currentEmployee} for this {@link Projects} object.
     */
    @Nullable
    public Employee getCurrentEmployee() {
        return this.currentEmployee;
    }

    public void signIn(final Employee newEmployee) {
        this.currentEmployee = newEmployee;
    }

    public static void main (final String[] args) {
        launch(args);
    }

    /**
     * Gets the {@link #DATABASE}.
     *
     * @return The {@link #DATABASE} object.
     */
    public static Database getDatabase() {
        return DATABASE;
    }

}
