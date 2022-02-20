package bd.edu.seu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QueryController implements Initializable {
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField idField;
    @FXML
    TextField textField1;
    @FXML
    TextField textField2;
    @FXML
    TextArea textArea;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    DBActions dbActions;


    public QueryController() {
        choiceBox = new ChoiceBox<>();
        idField = new TextField();
        textField1 = new TextField();
        textField2 = new TextField();
        textArea = new TextArea();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        dbActions = new DBActions();
    }

    private void setVisible() {
        textField1.setVisible(true);
        textField2.setVisible(true);
        textArea.setVisible(true);

    }

    private void clear() {
        textField1.setVisible(false);
        textField2.setVisible(false);
        textArea.setVisible(false);
        label1.setText(null);
        label2.setText(null);
        label3.setText(null);
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void handleSearchButton() throws IOException {
        clear();
        if (choiceBox.getValue() != null) {
            label1.setText(null);
            if (choiceBox.getValue().contains("ID")) {
                if (!idField.getText().isEmpty()) {
                    if (choiceBox.getValue().contains("User")) {
                        User user = dbActions.getUserByUserId(idField.getText());
                        if (user != null) {
                            label1.setText("Name:");
                            label2.setText("Email:");
                            label3.setText("Number of downloads:");
                            setVisible();
                            textArea.setText(user.getEmail());
                            textField1.setText(user.getName());
                            textField2.setText(String.valueOf(user.getNumberOfDownloads()));
                        } else {
                            label1.setText("No user found");
                        }
                    } else if (choiceBox.getValue().contains("Project")) {
                        Project project = dbActions.getProjectByProjectId(idField.getText());
                        if (project != null) {
                            label1.setText("Owned by ID:");
                            label2.setText("Description:");
                            label3.setText("Number of downloads:");
                            setVisible();
                            textArea.setText(project.getDescription());
                            textField1.setText(project.getOwnedBy());
                            textField2.setText(String.valueOf(project.getDownloaded()));
                        } else {
                            label1.setText("No project found");
                        }
                    } else {
                        Bug bug = dbActions.getBugByBugId(idField.getText());
                        if (bug != null) {
                            label1.setText("Project ID:");
                            label2.setText("Description:");
                            label3.setText("Reported by:");
                            setVisible();
                            textArea.setText(bug.getDescription());
                            textField1.setText(bug.getProjectId());
                            textField2.setText(bug.getUserId());
                        } else {
                            label1.setText("No Bug report found");
                        }
                    }
                } else {
                    idField.setPromptText("Fill this field");
                }
            } else {
                if(choiceBox.getValue().endsWith("users")){
                    App.setRoot("allUserQuery");
                }
                else if (choiceBox.getValue().endsWith("projects")){
                    App.setRoot("allProjectQuery");
                }
                else if(choiceBox.getValue().endsWith("reports")){
                    App.setRoot("allBugReportQuery");
                }
                else{
                    App.setRoot("allTransactionQuery");
                }
            }
        } else {
            label1.setText("Select search type");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clear();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Search by User ID");
        observableList.add("Search by Project ID");
        observableList.add("Search by Bug ID");
        observableList.add("Show all users");
        observableList.add("Show all projects");
        observableList.add("Show all bug reports");
        observableList.add("Show all transactions");
        choiceBox.setItems(observableList);
    }
}
