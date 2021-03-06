package com.harleyoconnor.projects;

import com.harleyoconnor.projects.gui.DashboardScreen;
import com.harleyoconnor.projects.gui.Screen;
import com.harleyoconnor.projects.gui.SignInScreen;
import com.harleyoconnor.projects.gui.manipulator.SceneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StackPaneManipulator;
import com.harleyoconnor.projects.gui.manipulator.StageManipulator;
import com.harleyoconnor.projects.gui.stylesheets.StylesheetManager;
import com.harleyoconnor.projects.gui.stylesheets.ThemedStylesheet;
import com.harleyoconnor.projects.object.Department;
import com.harleyoconnor.projects.object.Employee;
import com.harleyoconnor.projects.object.MeetingRoom;
import com.harleyoconnor.projects.object.MeetingRoomBooking;
import com.harleyoconnor.projects.util.Injected;
import com.harleyoconnor.projects.util.ReflectionHelper;
import com.harleyoconnor.serdes.database.DefaultDatabase;
import com.harleyoconnor.serdes.util.SQLHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Date;

/**
 * The main {@link Application} class.
 *
 * @author Harley O'Connor
 */
public final class Projects extends Application {

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
        // Set the INSTANCE reference to this.
        ReflectionHelper.setFieldUnchecked(this.getClass(), "INSTANCE", this);

        // Set the default database.
        DefaultDatabase.set(() -> SQLHelper.getConnectionUnsafe("mariadb", DatabaseConstants.IP, DatabaseConstants.PORT,
                DatabaseConstants.SCHEMA, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD));
    }

    private Projects(@SuppressWarnings("unused") @Nullable Object placeholder) {}

    @Override
    public void start(final Stage primaryStage) {
        ReflectionHelper.setFieldUnchecked(this, "primaryStage", StageManipulator.of(primaryStage));

        this.primaryStage.minWidth(Constants.MIN_WIDTH).minHeight(Constants.MIN_HEIGHT)
                .width(Constants.DEFAULT_WIDTH).height(Constants.DEFAULT_HEIGHT).title("Projects")
                .scene(this.primaryScene.get()).show();

        // Add the default stylesheet.
        STYLESHEET_MANAGER.addStylesheets(this.primaryScene.get().getStylesheets(), new ThemedStylesheet("default"));

//        MeetingRoomBooking.SER_DES.createTable();
//
//        new MeetingRoom().setCapacity(2).serialise();
//        new MeetingRoom().setCapacity(4).serialise();
//        new MeetingRoom().setCapacity(8).serialise();
//        new MeetingRoom().setCapacity(15).setWheelchairAccess(true).serialise();
//        new MeetingRoom().setCapacity(50).serialise();
//
//        final var department = new Department().setName("Software Department");
//        final var employee = new Employee().setFirstName("Harley").setLastName("O'Connor")
//                .setEmail("thisismyemail@gmail.com").hashAndSetPassword("secure").setWage(20.45);
//
//        employee.serialise();
//        department.setHead(employee);
//        department.serialise();
//
//        employee.setDepartment(department);
//        employee.serialise();
//
//        final var booking = new MeetingRoomBooking(employee,
//                MeetingRoom.SER_DES.deserialise(2)).setTime(Date.from(Instant.now()));
//
//        booking.serialise();
//
//        final var bookings = employee.getBookings();
//
//        employee.getDepartment().getEmployees().forEach(System.out::println);
//        bookings.forEach(System.out::println);

        // Create and show the sign in screen.
//        new SignInScreen(this.primaryStage, this.primaryScene, this.primaryView.toPaneManipulator(), null).show();
        this.showInitialScreen(new DashboardScreen(this.primaryStage, this.primaryScene, this.primaryView.toPaneManipulator(), null, Employee.fromEmail("thisismyemail@gmail.com")));
    }

    private void showInitialScreen(final Screen<?, ?> screen) {
        screen.show();
        this.primaryStage.title(screen.getTitle());
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

}
