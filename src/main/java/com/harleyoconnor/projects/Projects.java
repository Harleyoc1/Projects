package com.harleyoconnor.projects;

import com.harleyoconnor.projects.gui.SignInScreen;
import com.harleyoconnor.projects.gui.manipulator.SceneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StackPaneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StageManipulator;
import com.harleyoconnor.projects.object.Employee;
import com.harleyoconnor.projects.serialisation.util.SQLHelper;
import com.harleyoconnor.projects.util.Injected;
import com.harleyoconnor.projects.util.ReflectionHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.annotation.Nullable;

/**
 * @author Harley O'Connor
 */
public final class Projects extends Application {

    private static final DatabaseController DATABASE_CONTROLLER = new DatabaseController(
            SQLHelper.getConnectionUnsafe("mariadb", DatabaseConstants.IP, DatabaseConstants.PORT,
                    DatabaseConstants.SCHEMA, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD));

    @Injected
    public static final Projects INSTANCE = null;

    private final StackPaneManipulator<StackPane> primaryView = StackPaneManipulator.create();
    private final SceneManipulator<Scene> primaryScene = SceneManipulator.create(this.primaryView.get());

    @Injected
    private final StageManipulator<Stage> primaryStage = null;

    private Employee currentEmployee;

    public Projects() {
        ReflectionHelper.setFieldUnchecked(this.getClass(), "INSTANCE", this);
    }

    @Override
    public void start(final Stage primaryStage) {
        ReflectionHelper.setFieldUnchecked(this, "primaryStage", StageManipulator.of(primaryStage));

        assert this.primaryStage != null;

        this.primaryStage.minWidth(Constants.MIN_WIDTH).minHeight(Constants.MIN_HEIGHT)
                .width(Constants.DEFAULT_WIDTH).height(Constants.DEFAULT_HEIGHT).title("Projects")
                .scene(this.primaryScene.get()).show();

        new SignInScreen(this.primaryStage, this.primaryScene, this.primaryView.toPaneManipulator(), null).show();
    }

    @Nullable
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void signIn(final Employee newEmployee) {
        this.currentEmployee = newEmployee;
    }

    public static void main (final String[] args) {
        launch(args);
    }

    public static DatabaseController getDatabaseController() {
        return DATABASE_CONTROLLER;
    }

}
