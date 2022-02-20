package bd.edu.seu;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateUserController {
    Boolean isGuestCheck = false;
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    CheckBox guestCheck;
    @FXML
    Label enterPasswordLabel;
    @FXML
    Label confirmPasswordLabel;
    @FXML
    PasswordField enterPasswordField;
    @FXML
    PasswordField confirmPasswordField;
    @FXML
    CheckBox developerCheck;
    @FXML
    Label warningLabel;
    DBActions dbActions;

    public CreateUserController() {
        nameField = new TextField();
        emailField = new TextField();
        guestCheck = new CheckBox();
        enterPasswordLabel = new Label();
        confirmPasswordLabel = new Label();
        enterPasswordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        developerCheck = new CheckBox();
        warningLabel = new Label();
        dbActions = new DBActions();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        guestCheck.setSelected(false);
        enterPasswordField.setText("");
        confirmPasswordField.setText("");
        developerCheck.setSelected(false);
    }

    @FXML
    private void handleCreateButton() throws IOException {
        if (nameField.getText().equals("") || emailField.getText().equals("")) {
            warningLabel.setText("Fill up all the Fields!");
        } else if (!emailField.getText().contains("@")) {
            warningLabel.setText("invalid mail format");
        } else {
            if (!isGuestCheck && (!enterPasswordField.getText().equals(confirmPasswordField.getText()) || confirmPasswordField.getText().equals(""))) {
                warningLabel.setText("Passwords didn't matched!");
            } else if (confirmPasswordField.getText().length() > 8) {
                warningLabel.setText("Passwords can be at most 8 Characters");
            } else {
                String id = dbActions.createId("userId",  "user_id" , "user_id");
                String name = nameField.getText();
                String email = emailField.getText();
                String password = confirmPasswordField.getText();
                User user = new User(name, id, email, 0);
                Boolean isDeveloper = developerCheck.isSelected();
                if (dbActions.createUser(user, password, isDeveloper, isGuestCheck)) {
                    clearForm();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Account Created with userId ID: %s.Use this ID to Log In", id));

                    alert.showAndWait();
                    App.setRoot("logIn");
                } else {
                    System.out.println("userId creation failed");
                }
            }
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("logIn");
    }

    @FXML
    private void handleGuestUserSelection() {
        isGuestCheck = guestCheck.isSelected();
        enterPasswordLabel.setDisable(isGuestCheck);
        enterPasswordField.setDisable(isGuestCheck);
        confirmPasswordLabel.setDisable(isGuestCheck);
        confirmPasswordField.setDisable(isGuestCheck);
        if (developerCheck.isSelected()) {
            developerCheck.setSelected(!isGuestCheck);
        }
        developerCheck.setDisable(isGuestCheck);
        enterPasswordField.clear();
        confirmPasswordField.clear();
        warningLabel.setText(null);
        System.out.println(isGuestCheck);
    }
}