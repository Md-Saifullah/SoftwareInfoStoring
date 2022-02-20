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

public class AllProjectQueryController implements Initializable {
    @FXML
    TableView<Project> tableView;
    @FXML
    TableColumn<Project, String> idColumn;
    @FXML
    TableColumn<Project, String> ownedByColumn;
    @FXML
    TableColumn<Project, Character> statusColumn;
    @FXML
    TableColumn<Project, Integer> downloadedColumn;
    @FXML
    TableColumn<Project, Integer> bugColumn;
    @FXML
    TableColumn<Project,String>descriptionColumn;
    DBActions dbActions;

    public AllProjectQueryController() {
        tableView = new TableView<>();
        idColumn = new TableColumn<>();
        ownedByColumn = new TableColumn<>();
        statusColumn = new TableColumn<>();
        downloadedColumn = new TableColumn<>();
        bugColumn = new TableColumn<>();
        descriptionColumn = new TableColumn<>();
        dbActions = new DBActions();
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("query");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        projects.addAll(dbActions.getAllProject());
        ownedByColumn.setCellValueFactory(new PropertyValueFactory<>("ownedBy"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        downloadedColumn.setCellValueFactory(new PropertyValueFactory<>("downloaded"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        bugColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfBugReport"));
        tableView.setItems(projects);
    }
}
