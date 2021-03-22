package com.harleyoconnor.projects;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Harley O'Connor
 */
public final class Projects extends Application {

    private static final Logger LOADING_LOGGER = Logger.getLogger("Loading");
    private static final DatabaseController DATABASE_CONTROLLER;

    static {
        final Logger logger = Logger.getLogger("Loading");

        try {
            DATABASE_CONTROLLER = new DatabaseController(DriverManager.getConnection("jdbc:mariadb://" + DatabaseConstants.IP + ":" + DatabaseConstants.PORT + "/" + DatabaseConstants.SCHEMA, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD));
        } catch (final SQLException e) {
            logger.severe("Could not connect to database.");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.show();
    }

    public static void main (final String[] args) {
        launch(args);
    }

    public static DatabaseController getDatabaseController() {
        return DATABASE_CONTROLLER;
    }

}
