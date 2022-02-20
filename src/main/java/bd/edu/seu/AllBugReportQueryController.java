package bd.edu.seu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllBugReportQueryController implements Initializable {

    @FXML
    TableView<Bug> tableView;
    @FXML
    TableColumn<Bug, String> bugIdColumn;
    @FXML
    TableColumn<Bug, String> projectIdColumn;
    @FXML
    TableColumn<Bug, Character> reportedByColumn;
    @FXML
    TableColumn<Bug, String> dateColumn;
    @FXML
    TableColumn<Bug,String>descriptionColumn;
    DBActions dbActions;

    public AllBugReportQueryController() {
        tableView = new TableView<>();
        bugIdColumn = new TableColumn<>();
        projectIdColumn = new TableColumn<>();
        reportedByColumn = new TableColumn<>();
        dateColumn = new TableColumn<>();
        descriptionColumn = new TableColumn<>();
        dbActions = new DBActions();
    }
    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("query");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Bug> bugs = FXCollections.observableArrayList();
        bugs.addAll(dbActions.getAllBugs());
        projectIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        bugIdColumn.setCellValueFactory(new PropertyValueFactory<>("bugId"));
        reportedByColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(bugs);
    }
}
