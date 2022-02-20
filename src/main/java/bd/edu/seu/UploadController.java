package bd.edu.seu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UploadController implements Initializable {

    @FXML
    CheckBox catA;
    @FXML
    CheckBox catB;
    @FXML
    CheckBox catC;
    @FXML
    CheckBox catD;
    @FXML
    CheckBox statusD;
    @FXML
    CheckBox statusP;
    @FXML
    ChoiceBox<String> choiceBoxDepends;
    @FXML
    TextArea descriptionArea;
    @FXML
    Label warningLabel;
    DBActions dbActions;
    String user;

    public UploadController() {
        catA=new CheckBox();
        catB=new CheckBox();
        catC=new CheckBox();
        catD=new CheckBox();
        statusD=new CheckBox();
        statusP=new CheckBox();
        choiceBoxDepends=new ChoiceBox<>();
        descriptionArea=new TextArea();
        dbActions=new DBActions();
        warningLabel=new Label();
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void handleStatusDCheck(){
        if(statusD.isSelected()){
            statusP.setSelected(false);
        }
    }
    @FXML
    private void handleStatusPCheck(){
        if(statusP.isSelected()){
            statusD.setSelected(false);
        }
    }
    @FXML
    private void handleUploadButton() throws IOException {
        System.out.println("handleUploadButton");
        String depends=choiceBoxDepends.getValue();
        String description;
        String category="";
        char status;
        if(catA.isSelected()||catB.isSelected()||catC.isSelected()||catD.isSelected()){
            if(catA.isSelected()){
                category+="A, ";
            }
            if(catB.isSelected()){
                category+="B, ";
            }
            if(catC.isSelected()){
                category+="C, ";
            }
            if(catD.isSelected()){
                category+="D, ";
            }
            if(!descriptionArea.getText().isEmpty()){
                description=descriptionArea.getText();
                if(statusD.isSelected()||statusP.isSelected()){
                    if(statusD.isSelected()){
                        status='D';
                    }
                    else {
                        status='P';
                    }
                    warningLabel.setText("null");
                    String id=dbActions.createId("project","project_id","project_id");
                    Project project=new Project(id,status,user,description,0,0);
                    if(dbActions.uploadProject(project,category,depends)) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText(String.format("Project uploaded with project ID: %s.", id));
                        alert.showAndWait();
                        App.setRoot("mainMenu");
                    }
                    System.out.println(project+category+depends);
                }
                else{
                    warningLabel.setText("Select a status");
                }
            }
            else {
                warningLabel.setText("Fill the description field");
            }
        }
        else {
            warningLabel.setText("Select a category");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user=App.getCurrentUser();
        ObservableList<String> projectList= FXCollections.observableArrayList();
        projectList.addAll(dbActions.getAllProjectNames());
        choiceBoxDepends.setItems(projectList);
    }
}
