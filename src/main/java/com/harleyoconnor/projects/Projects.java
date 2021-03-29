package com.harleyoconnor.projects;

import com.harleyoconnor.projects.objects.Employee;
import com.harleyoconnor.projects.serialisation.util.SQLHelper;
import javafx.application.Application;
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
