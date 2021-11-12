package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Owner;
import util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminLogInFormController implements Initializable {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnUpdateAdminLogIn;

    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();
    Pattern userNamePattern=Pattern.compile("^[[A-z]+[0-9]*[_]?]{8,15}$");
    Pattern passwordPattern=Pattern.compile("^(?=.*[A-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-z\\d@$!%*#?&]{8}$");

    public void tableCodeKeyReleased(KeyEvent keyEvent) {
        Object response= Validation.validate(map,btnUpdateAdminLogIn);
        if(response instanceof  TextField){
            TextField errorText=(TextField)response;
            if(keyEvent.getCode()== KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnUpdateAdminLogIn.setDisable(true);
        }else if(response instanceof Boolean){
            btnUpdateAdminLogIn.setDisable(false);
        }
    }

    public void updateLogInDetailOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name= String.valueOf(checkUserName());
        String password=String.valueOf(checkPassword());
        Owner o1=new Owner(
                name,
                password
        );
        if(new ManageUserController().updateAdmin(o1)){
            new Alert(Alert.AlertType.INFORMATION,"Update admin details in the user table to update successfully ").show();
            btnUpdateAdminLogIn.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again 😑").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdateAdminLogIn.setDisable(true);
        validateInit();
        String password= null;
        String name=null;
        try {
            password = new ManageUserController().getAdminPassword();
             name=new ManageUserController().getAdminUserName();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        char[] charPassword=password.toCharArray();
        for(int i=0;i<charPassword.length;i++){
            charPassword[i]-=5;
        }
        char[] charName=name.toCharArray();
        for(int i=0;i<charName.length;i++){
            charName[i]-=5;
        }
        txtUserName.setText(String.valueOf(charName));
        txtPassword.setText(String.valueOf(charPassword));
    }

    private void validateInit() {
        map.put(txtUserName,userNamePattern);
        map.put(txtPassword,passwordPattern);
    }


    private char[] checkPassword() {
        String password=txtPassword.getText();
        char[] charPassword=password.toCharArray();
        for(int i=0;i<charPassword.length;i++){
            charPassword[i]+=5;
        }
        return charPassword;
    }

    private char[] checkUserName() {
        String name=txtUserName.getText();
        char[] charName=name.toCharArray();
        for(int i=0;i<charName.length;i++){
            charName[i]+=5;
        }
        return charName;
    }
}
