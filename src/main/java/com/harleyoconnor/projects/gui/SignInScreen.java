package com.harleyoconnor.projects.gui;

import com.harleyoconnor.projects.Projects;
import com.harleyoconnor.projects.gui.manipulator.*;
import com.harleyoconnor.projects.object.Employee;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Harley O'Connor
 */
public final class SignInScreen extends Screen<HBox, HBoxManipulator<HBox>> {

    private final TextFieldManipulator<TextField> emailField = TextFieldManipulator.create().placeholder("Email").onEnter(this::onSignInPress);
    private final TextFieldManipulator<PasswordField> passwordField = TextFieldManipulator.of(new PasswordField()).placeholder("Password").onEnter(this::onSignInPress);
    private final LabelManipulator<Label> errorLabel = LabelManipulator.create().body().wrapText();

    public SignInScreen(StageManipulator<Stage> stage, SceneManipulator<Scene> scene, PaneManipulator<Pane, ?> parentView, Screen<?, ?> previousScreen) {
        super(stage, scene, parentView, previousScreen, "Sign In");
        this.setupLayout();
    }

    @Override
    protected HBoxManipulator<HBox> initialiseLayout() {
        return HBoxManipulator.create();
    }

    private void setupLayout() {
        this.layout.add(VBoxManipulator.create().add(LabelManipulator.create().text("Sign In").title().wrapText(), this.emailField, this.passwordField,
                HBoxManipulator.create().add(RegionManipulator.horizontalSpacer(), ButtonManipulator.create().text("Sign In").onAction(this::onSignInPress)),
                this.errorLabel).spacing().fixWidth(300).padding(25).centre()).centre();
    }

    private void onSignInPress(final Event event) {
        final var email = this.emailField.get().getText();

        if (!Employee.emailExists(email)) {
            this.errorLabel.text("The entered email is not registered.");
            return;
        }

        final var employee = Employee.fromEmail(email);
        final var password = this.passwordField.get().getText();

        if (!employee.authenticate(password)) {
            // A warning like this usually isn't a good idea, but this is only intended to be an internal system.
            this.errorLabel.text("The entered password was incorrect.");
            return;
        }

        Projects.INSTANCE.signIn(employee);
    }

}
