package bd.edu.seu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    CheckBox ownedCheck;
    @FXML
    TextArea descriptionArea;
    @FXML
    TextField idField;
    @FXML
    TextField statusField;
    @FXML
    TextField ownedByIdField;
    @FXML
    TableView<Project> tableView;
    @FXML
    TableColumn<Project, String> ownedByColumn;
    @FXML
    TableColumn<Project, String> idColumn;
    @FXML
    Button bugButton;
    @FXML
    Button downloadButton;
    @FXML
    Button cancelButton;
    @FXML
    Button uploadButton;
    @FXML
    Button updateButton;
    String user;
    DBActions dbActions;
    Project selectedProject;

    public MainMenuController() {
        ownedCheck = new CheckBox();
        descriptionArea = new TextArea();
        idField = new TextField();
        statusField = new TextField();
        ownedByIdField = new TextField();
        tableView = new TableView<>();
        ownedByColumn = new TableColumn<>();
        idColumn = new TableColumn<>();
        dbActions = new DBActions();
    }

    @FXML
    private void handleLogOutButton() throws IOException {
        App.setDeveloper(false);
        App.setSubscriber(false);
        App.setCurrentUser(null);
        App.setRoot("logIn");
    }

    @FXML
    private void handleQueryButton() throws IOException {
        App.setRoot("query");
    }

    @FXML
    private void handleOwnedCheckSelection() throws IOException {
        App.setOwnedCheck(ownedCheck.isSelected());
        App.setRoot("mainMenu");
    }

    @FXML
    private void handleUploadButton() throws IOException {
        App.setRoot("upload");
    }

    private void setDataOnForm(Project project) {
        idField.setText(project.getProjectId());
        statusField.setText(String.valueOf(project.getStatus()));
        ownedByIdField.setText(project.getOwnedBy());
        descriptionArea.setText(project.getDescription());
    }

    @FXML
    private void handleSelectFromTable() {
        selectedProject = tableView.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            if (App.isSubscriber()) {
                bugButton.setDisable(false);
                if (selectedProject.getOwnedBy().equals(user)) {
                    updateButton.setDisable(false);
                }
            }
            downloadButton.setDisable(false);
            cancelButton.setDisable(false);
            setDataOnForm(selectedProject);
        }
    }

    @FXML
    private void handleCancelButton() {
        idField.clear();
        statusField.clear();
        descriptionArea.clear();
        ownedByIdField.clear();
        downloadButton.setDisable(true);
        cancelButton.setDisable(true);
        bugButton.setDisable(true);
        updateButton.setDisable(true);
    }
    @FXML
    private void handleDownloadButton(){
        dbActions.updateDownload(user,idField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Project added to downloads");
        alert.showAndWait();
        handleCancelButton();
    }
    @FXML
    private void handleBugReportButton() throws IOException {
        App.setRoot("reportBug");
    }
    @FXML
    private void handleUpdateButton() throws IOException {
        App.setCurrentProject(selectedProject.getProjectId());
        App.setRoot("update");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = App.getCurrentUser();
        App.setDeveloper(dbActions.isDeveloper(user));
        bugButton.setDisable(true);
        downloadButton.setDisable(true);
        updateButton.setDisable(true);
        cancelButton.setDisable(true);
        ObservableList<Project> projects = FXCollections.observableArrayList();
        if (App.isDeveloper()) {
            if (App.getOwnedCheck()) {
                ownedCheck.setSelected(true);
                projects.addAll(dbActions.getProjectByUserId(user));
            } else {
                projects.addAll(dbActions.getAllProject());
            }
        } else {
            uploadButton.setDisable(true);
            projects.addAll(dbActions.getAllProject());
            ownedCheck.setDisable(true);
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        ownedByColumn.setCellValueFactory(new PropertyValueFactory<>("ownedBy"));
        tableView.setItems(projects);
    }
}
