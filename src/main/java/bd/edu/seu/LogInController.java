package bd.edu.seu;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInController {
    @FXML
    CheckBox guestCheckBox;
    @FXML
    TextField idField;
    @FXML
    Label passwordLabel;
    @FXML
    PasswordField passwordField;
    @FXML
    Label warningLabel;
    DBActions dbActions;

    public LogInController() {
        guestCheckBox = new CheckBox();
        idField = new TextField();
        passwordLabel = new Label();
        passwordField = new PasswordField();
        warningLabel = new Label();
        dbActions = new DBActions();
    }

    @FXML
    private void handleGuestCheckSelection() {
        warningLabel.setText(null);
        passwordField.clear();
        passwordLabel.setDisable(guestCheckBox.isSelected());
        passwordField.setDisable(guestCheckBox.isSelected());
    }

    @FXML
    private void handleLogInButton() throws IOException {
        if (idField.getText().equals("") || (!guestCheckBox.isSelected() && passwordField.getText().equals(""))) {
            warningLabel.setText("Fill up all the fields!");
        } else {
            String id = idField.getText();
            Boolean authenticationCheck;
            App.setSubscriber(false);
            if (guestCheckBox.isSelected()) {
                authenticationCheck = dbActions.isGuest(id);
            } else {
                String password = passwordField.getText();
                authenticationCheck = dbActions.isSubscriber(id, password);
                App.setSubscriber(authenticationCheck);
            }
            if (authenticationCheck) {
                App.setCurrentUser(id);
                App.setRoot("mainMenu");
            } else {
                warningLabel.setText("Invalid User ID or Password");
            }
        }
    }

    @FXML
    private void handleCreateUserButton() throws IOException {
        App.setRoot("createUser");
    }
}
