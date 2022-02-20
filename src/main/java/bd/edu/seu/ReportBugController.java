package bd.edu.seu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportBugController implements Initializable {
    @FXML
    ChoiceBox<String> choiceBoxProject;
    @FXML
    TextArea descriptionArea;
    @FXML
    Label warningLabel;
    String user;
    DBActions dbActions;

    public ReportBugController() {
        choiceBoxProject = new ChoiceBox<>();
        descriptionArea = new TextArea();
        warningLabel = new Label();
        dbActions = new DBActions();
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void handleReportButton() throws IOException {
        System.out.println("handleReportButton");
        if (choiceBoxProject.getValue()!=null) {
            if (!descriptionArea.getText().isEmpty()) {
                warningLabel.setText(null);
                String projectId=choiceBoxProject.getValue();
                String description=descriptionArea.getText();
                String bugId=dbActions.createId("bug","bug_id","bug_id");
                Bug bug=new Bug(bugId,description, LocalDate.now(),user,projectId);
                if(dbActions.createBugReport(bug)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("Reported Bug with Bug ID: %s.", bugId));
                    alert.showAndWait();
                    App.setRoot("mainMenu");
                }
            } else {
                warningLabel.setText("Fill the description field");
            }
        } else {
            warningLabel.setText("Select a Project ID first");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = App.getCurrentUser();
        ObservableList<String> projectList = FXCollections.observableArrayList();
        projectList.addAll(dbActions.getAllProjectNames());
        choiceBoxProject.setItems(projectList);
    }
}
