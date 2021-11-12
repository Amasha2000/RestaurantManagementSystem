package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class LogInFormController implements Initializable {
    public JFXTextField txtName;
    public JFXPasswordField txtPassword;
    public AnchorPane logInContext;
    public ImageView hungryImage;
    public JFXButton btnCashier;
    public JFXButton btnManagement;


    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();
    Pattern patternUserName=Pattern.compile("^[[A-z]+[0-9]*[_]?]{8,15}$");
    Pattern patternPassword=Pattern.compile("^(?=.*[A-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-z\\d@$!%*#?&]{8}$");
    
    public void managementLogInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String password=txtPassword.getText();
        char[] charPassword=password.toCharArray();
        for(int i=0;i<charPassword.length;i++){
            charPassword[i]+=5;
        }
        String name=txtName.getText();
        char[] charName=name.toCharArray();
        for(int i=0;i<charName.length;i++){
            charName[i]+=5;
        }
        if(new ManageLogInController().getAdminDetails(String.valueOf(charName), String.valueOf(charPassword))){
            Stage window = (Stage) logInContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagementDashboardForm.fxml"))));
        }else {
            new Alert(Alert.AlertType.WARNING, "Please Enter Correct User Name & Password").showAndWait();
        }
    }

    public void cashierLogInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String password=txtPassword.getText();
        char[] charPassword=password.toCharArray();
        for(int i=0;i<charPassword.length;i++){
            charPassword[i]+=5;
        }
        String name=txtName.getText();
        char[] charName=name.toCharArray();
        for(int i=0;i<charName.length;i++){
            charName[i]+=5;
        }
       if(new ManageLogInController().getUserDetails(String.valueOf(charName), String.valueOf(charPassword))){
           Stage window = (Stage) logInContext.getScene().getWindow();
           window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"))));
       }else {
           new Alert(Alert.AlertType.WARNING, "Please Enter Correct User Name & Password").showAndWait();
       }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCashier.setDisable(true);
        btnManagement.setDisable(true);
        validateInit();
    }

    private void validateInit() {
        map.put(txtName,patternUserName);
        map.put(txtPassword,patternPassword);
    }

    public void codeReleased(KeyEvent keyEvent) {
        Object response= validation(map,btnCashier,btnManagement);
        if(response instanceof  TextField){
            TextField errorText=(TextField)response;
            if(keyEvent.getCode()== KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnCashier.setDisable(true);
            btnManagement.setDisable(true);
        }else if(response instanceof Boolean){
            btnCashier.setDisable(false);
            btnManagement.setDisable(false);
        }
    }

    private Object validation(LinkedHashMap<TextField, Pattern> map, JFXButton btnCashier, JFXButton btnManagement) {
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                    ((AnchorPane) textFieldKey.getParent()).getChildren().get(0).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
            ((AnchorPane) textFieldKey.getParent()).getChildren().get(0).setStyle("-fx-text-fill: green;"+"-fx-background-color: white;");
        }
        return true;
    }
}
