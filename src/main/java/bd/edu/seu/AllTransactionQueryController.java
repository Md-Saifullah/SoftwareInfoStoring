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

public class AllTransactionQueryController implements Initializable {
    @FXML
    TableView<Transaction> tableView;
    @FXML
    TableColumn<Transaction, String> transactionIdColumn;
    @FXML
    TableColumn<Transaction, String> projectIdColumn;
    @FXML
    TableColumn<Transaction, String> dateColumn;
    @FXML
    TableColumn<Transaction,String> transactionTypeColumn;
    DBActions dbActions;

    public AllTransactionQueryController() {
        tableView = new TableView<>();
        transactionIdColumn = new TableColumn<>();
        projectIdColumn = new TableColumn<>();
        dateColumn = new TableColumn<>();
        transactionTypeColumn = new TableColumn<>();
        dbActions = new DBActions();
    }
    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("query");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        transactions.addAll(dbActions.getAllTransactions());
        projectIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        tableView.setItems(transactions);
    }
}
