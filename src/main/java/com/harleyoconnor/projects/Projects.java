package com.harleyoconnor.projects;

import com.harleyoconnor.projects.objects.Employee;
import com.harleyoconnor.projects.serialisation.util.SQLHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * @author Harley O'Connor
 */
public final class Projects extends Application {

    private static final Logger LOADING_LOGGER = Logger.getLogger("Loading");
    private static final DatabaseController DATABASE_CONTROLLER;

    static {
        DATABASE_CONTROLLER = new DatabaseController(SQLHelper.getConnectionUnsafe("mariadb", DatabaseConstants.IP, DatabaseConstants.PORT, DatabaseConstants.SCHEMA, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD));

        Employee.SER_DES.getFields();

        final var sameEmployee = Employee.fromEmail("thisismyemail@harleyoconnor.com");
        final var archieAdams = Employee.fromEmail("thisisarchieadamsemail@harleyoconnor.com");

        LOADING_LOGGER.info(sameEmployee.toString() + " " + sameEmployee.equals(archieAdams) + " Hash Codes: " + archieAdams.hashCode() + " " + sameEmployee.hashCode());
    }

    private final StackPane primaryView = new StackPane();
    private final Scene primaryScene = new Scene(this.primaryView);

    @Override
    public void start(final Stage primaryStage) {
        this.setupBasicProperties(primaryStage).setScene(this.primaryScene);

        primaryStage.show();
    }

    /**
     * Sets up basic properties for the main {@link Stage}.
     *
     * @param primaryStage The primary {@link Stage} {@link Object}.
     * @return The {@link Stage} {@link Object} given for chaining.
     */
    private Stage setupBasicProperties (final Stage primaryStage) {
        // Set minimum and default widths and heights.
        primaryStage.setMinWidth(Constants.MIN_WIDTH);
        primaryStage.setMinHeight(Constants.MIN_HEIGHT);
        primaryStage.setWidth(Constants.DEFAULT_WIDTH);
        primaryStage.setHeight(Constants.DEFAULT_HEIGHT);

        // Set the title of the scene.
        primaryStage.setTitle("Projects");

        return primaryStage;
    }

    public static void main (final String[] args) {
        launch(args);
    }

    public static DatabaseController getDatabaseController() {
        return DATABASE_CONTROLLER;
    }

}
