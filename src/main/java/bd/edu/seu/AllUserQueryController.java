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

public class AllUserQueryController implements Initializable {
    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, String> idColumn;
    @FXML
    TableColumn<User, String> nameColumn;
    @FXML
    TableColumn<User, String> emailColumn;
    @FXML
    TableColumn<User, Integer> noOfDownloadsColumn;
    DBActions dbActions;

    public AllUserQueryController() {
        tableView = new TableView<>();
        idColumn = new TableColumn<>();
        nameColumn = new TableColumn<>();
        emailColumn = new TableColumn<>();
        noOfDownloadsColumn = new TableColumn<>();
        dbActions = new DBActions();
    }
    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("query");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(dbActions.getAllUser());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        noOfDownloadsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfDownloads"));
        tableView.setItems(users);
    }
}
