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

        final var employee = Employee.fromEmail("thisisntmyactualemail@harleyoconnor.com");
        final var sameEmployee = Employee.fromEmail("thisisntmyactualemail@harleyoconnor.com");

        employee.setPassword("securePassword123");
        employee.getSerDes().serialise(employee);

        LOADING_LOGGER.info(employee.toString() + " " + employee.equals(sameEmployee) + " Hash Codes: " + employee.hashCode() + " " + sameEmployee.hashCode());
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
