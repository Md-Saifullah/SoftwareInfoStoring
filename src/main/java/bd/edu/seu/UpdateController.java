package bd.edu.seu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {
    @FXML
    TextField nameField;
    @FXML
    TextArea descriptionArea;
    @FXML
    CheckBox statusP;
    @FXML
    CheckBox statusU;
    @FXML
    CheckBox type1;
    @FXML
    CheckBox type2;
    @FXML
    CheckBox type3;
    @FXML
    CheckBox type4;
    @FXML
    Label warningLabel;
    String userId;
    String projectId;
    DBActions dbActions;
    public UpdateController() {
        nameField = new TextField();
        descriptionArea = new TextArea();
        statusP = new CheckBox();
        statusU = new CheckBox();
        type1 = new CheckBox();
        type2 = new CheckBox();
        type3 = new CheckBox();
        type4 = new CheckBox();
        warningLabel = new Label();
        dbActions=new DBActions();
    }

    @FXML
    private void handleBackButton() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    private void handleUpdateButton() throws IOException {
        if (!nameField.getText().isEmpty()) {
            if (statusP.isSelected() || statusU.isSelected()) {
                if (!descriptionArea.getText().isEmpty()) {
                    if(type1.isSelected()||type2.isSelected()||type3.isSelected()||type4.isSelected()){
                        String id=dbActions.createId("patch","patch_id","patch_id");
                        String name=nameField.getText();
                        String description=descriptionArea.getText();
                        String status;
                        int type;
                        if(statusP.isSelected()){
                            status="P";
                        }else{
                            status="U";
                        }
                        if(type1.isSelected()){
                            type=1;
                        }else if(type2.isSelected()){
                            type=2;
                        }else if (type3.isSelected()){
                            type=3;
                        }else {
                            type=4;
                        }
                        Patch patch=new Patch(id,name,status,description,type);
                        if(dbActions.createUpdate(patch,projectId)){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText(String.format("Project updated with patch ID: %s.", id));
                            alert.showAndWait();
                            App.setCurrentProject(null);
                            App.setRoot("mainMenu");
                        }
                    }else{
                        warningLabel.setText("Select a type");
                    }
                } else {
                    warningLabel.setText("Fill up the description field");
                }
            } else {
                warningLabel.setText("Select a Status");
            }
        } else {
            warningLabel.setText("Fill up the name field");
        }
    }

    @FXML
    private void handleStatusPCHeck() {
        if (statusP.isSelected()) {
            statusU.setSelected(false);
        }
    }

    @FXML
    private void handleStatusUCHeck() {
        if (statusU.isSelected()) {
            statusP.setSelected(false);
        }
    }

    @FXML
    private void handleType1Check() {
        if (type1.isSelected()) {
            type2.setSelected(false);
            type3.setSelected(false);
            type4.setSelected(false);
        }
    }

    @FXML
    private void handleType2Check() {
        if (type2.isSelected()) {
            type1.setSelected(false);
            type3.setSelected(false);
            type4.setSelected(false);
        }
    }

    @FXML
    private void handleType3Check() {
        if (type3.isSelected()) {
            type2.setSelected(false);
            type1.setSelected(false);
            type4.setSelected(false);
        }
    }

    @FXML
    private void handleType4Check() {
        if (type4.isSelected()) {
            type2.setSelected(false);
            type3.setSelected(false);
            type1.setSelected(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId = App.getCurrentUser();
        projectId=App.getCurrentProject();

    }
}