package com.harleyoconnor.projects.gui;

import com.harleyoconnor.javautilities.util.FileUtils;
import com.harleyoconnor.projects.Constants;
import com.harleyoconnor.projects.gui.manipulator.*;
import com.harleyoconnor.projects.gui.theme.ThemedImageView;
import com.harleyoconnor.projects.gui.util.InterfaceUtils;
import com.harleyoconnor.projects.object.Employee;
import com.harleyoconnor.projects.object.MeetingRoomBooking;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * @author Harley O'Connor
 */
public final class DashboardScreen extends Screen<HBox, HBoxManipulator<HBox>> {

    private static final String COLUMN = "column";
    private static final String CELL = "cell";

    private final VBoxManipulator<VBox> employeesColumn = createColumn("Employees", this::addEmployee);
    private final VBoxManipulator<VBox> bookingsColumn = createColumn("Bookings", this::addBooking);

    private static VBoxManipulator<VBox> createColumn(final String title, final Consumer<ActionEvent> addEvent) {
        return VBoxManipulator.create().add(HBoxManipulator.create().add(
                LabelManipulator.create(title).padding(1, 0, 0, 0).title().get(),
                InterfaceUtils.createHorizontalSpacer(),
                ButtonManipulator.create().graphic(
                        new ThemedImageView("/icons/plus.png").getManipulator()
                                .preserveRatio().width(21).smooth().cache().get()).noBorder().noBackground().get()
                )
        ).border().styleClasses(COLUMN).padding();
    }

    private final Employee employee;

    public DashboardScreen(StageManipulator<Stage> stage, SceneManipulator<Scene> scene, PaneManipulator<Pane, ?> parentView, Screen<?, ?> previousScreen, Employee employee) {
        super(stage, scene, parentView, previousScreen, "Dashboard");
        this.employee = employee;

        final HBoxManipulator<HBox> panels = HBoxManipulator.create();

        if (employee.isHead()) {
            panels.addWithMargin(this.employeesColumn);
        }

        this.layout.add(panels.addWithMargin(this.bookingsColumn)).centre();
    }

    private void reloadEmployees() {
        this.employeesColumn.clear(CELL);

        this.employee.getDepartment().getEmployees().forEach(employee ->
            this.employeesColumn.addWithMargin(5, 0, 5, 0, HBoxManipulator.create()
                    .add(LabelManipulator.create(employee.getFirstName() + " " + employee.getLastName()))
                    .padding(5).styleClasses(CELL))
        );
    }

    private void addEmployee(final ActionEvent event) {
    }

    private void reloadBookings() {
        // Clear all cells from the bookings column.
        this.bookingsColumn.clear(CELL);

        this.employee.getBookings().forEach(booking ->
            this.bookingsColumn.addWithMargin(5, 0, 5, 0, createBookingCell(booking))
        );
    }

    private void addBooking(final ActionEvent event) {
    }

    private static HBoxManipulator<HBox> createBookingCell(final MeetingRoomBooking booking) {
        return HBoxManipulator.create().addWithMargin(0, 5, 0, 5,
                LabelManipulator.create(booking.getRoom().getName()).body(),
                VBoxManipulator.create().add(LabelManipulator.create(booking.getTimeFormatted()).subBody().greyText()).centre()
        ).padding(5).styleClasses(CELL);
    }

    @Override
    protected HBoxManipulator<HBox> initialiseLayout() {
        return HBoxManipulator.create();
    }

    @Override
    protected void onShow() {
        this.reloadEmployees();
        this.reloadBookings();
    }

}
