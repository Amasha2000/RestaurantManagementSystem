package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.Validation;
import view.tm.UserTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserDetailFormController implements Initializable {
    public JFXButton btnAddUser;
    public JFXButton btnUpdateUser;
    public TableColumn colDelete;
    public JFXTextField txtUserId;
    public JFXTextField txtName;
    public JFXTextField txtPassword;
    public JFXTextField txtSearch;
    public TableView<UserTM> tblUser;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public JFXButton changeAdminLogInDetails;

    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();
    Pattern userIdPattern=Pattern.compile("^[U][-]([0][1-9]|10)$");
    Pattern userNamePattern=Pattern.compile("^[[A-z]+[0-9]*[_]?]{8,15}$");
    Pattern passwordPattern=Pattern.compile("^(?=.*[A-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-z\\d@$!%*#?&]{8}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            btnAddUser.setDisable(true);
            btnUpdateUser.setDisable(true);
            validateInit();
            setUserDetail();
    }

    private void setUserDetail() {
        try {
            colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            colUserId.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colUserName.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colPassword.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
            colDelete.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            setUserDetailToTable(new ManageUserController().getAllUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadData(newValue);
        });
    }

    private void loadData(UserTM tm) {
        try {
            String password = tm.getPassword();
            char[] charPassword = password.toCharArray();
            for (int i = 0; i < charPassword.length; i++) {
                charPassword[i] -= 5;
            }
            String name = tm.getUserName();
            char[] charName = name.toCharArray();
            for (int i = 0; i < charName.length; i++) {
                charName[i] -= 5;
            }
            try {
                txtUserId.setText(tm.getUserId());
                txtName.setText(String.valueOf(charName));
                txtPassword.setText(String.valueOf(charPassword));
            } catch (NullPointerException e) {

            }
            txtUserId.setEditable(false);
        }catch (NullPointerException e){}
    }


    private void setUserDetailToTable(ArrayList<User> allUsers) {
        ObservableList<UserTM> userList= FXCollections.observableArrayList();
        allUsers.forEach(e->{
            Button btn=new Button("Delete");
            btn.setStyle("-fx-background-color:#81ecec");
            userList.add(new UserTM(e.getUserId(),e.getUserName(),e.getPassword(),btn));

            btn.setOnAction(event -> {
                ButtonType yes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                ButtonType no=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this user? 🤔",yes,no);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result=alert.showAndWait();
                if(result.orElse(yes)==no){

                }else{
                    try {
                        if(new ManageUserController().deleteUser(e.getUserId())){
                            new Alert(Alert.AlertType.CONFIRMATION, "Table is Deleted 👍").show();
                            setUserDetail();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            });
        });
        tblUser.setItems(userList);
    }

    private void validateInit() {
        map.put(txtUserId,userIdPattern);
        map.put(txtName,userNamePattern);
        map.put(txtPassword,passwordPattern);
    }


    public void tableCodeKeyReleased(KeyEvent keyEvent) {
        Object response=Validation.validate(map,btnAddUser);
        if(response instanceof  TextField){
            TextField errorText=(TextField)response;
            if(keyEvent.getCode()==KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnUpdateUser.setDisable(true);
        }else if(response instanceof Boolean){
            btnUpdateUser.setDisable(false);
        }

    }

    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if(!userIdPattern.matcher(txtSearch.getText()).matches()){
            if(!txtSearch.getText().isEmpty()){
                txtSearch.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
            }
        }else{
            txtSearch.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
            ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");
            if(keyEvent.getCode()==KeyCode.ENTER){
                String userId=txtSearch.getText();

                    ArrayList<User> u1=new ManageUserController().getUser(userId);
                    if(u1==null){
                        new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                    }else{
                        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
                        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
                        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
                        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
                        setUserDetailToTable(u1);

                        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                            if(newValue.isEmpty()){
                                setUserDetail();
                            }
                        });
                    }

                }
            }
        }

    public void updateUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name= String.valueOf(checkUserName());
        String password=String.valueOf(checkPassword());
        User u1=new User(
                txtUserId.getText(),
                name,
                password
        );
        if(new ManageUserController().updateUser(u1)){
            new Alert(Alert.AlertType.CONFIRMATION,"User Updated 👌").show();
            setUserDetail();
            txtUserId.clear();
            txtName.clear();
            txtPassword.clear();
            txtUserId.setEditable(false);
            btnAddUser.setDisable(true);
            btnUpdateUser.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Add User Details To Update 😑").show();
        }
    }

    public void addUserOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name= String.valueOf(checkUserName());
        String password= String.valueOf(checkPassword());

        User user=new User(
                txtUserId.getText(),
                name,
                password
        );
        if(new ManageUserController().saveUser(user)){
            new Alert(Alert.AlertType.CONFIRMATION, "New User is Added 👌").show();
            setUserDetail();
            txtUserId.clear();
            txtName.clear();
            txtPassword.clear();
            btnAddUser.setDisable(true);
            btnUpdateUser.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING, "User Id Or Password Already Exists 😑").show();
        }
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
        String name=txtName.getText();
        char[] charName=name.toCharArray();
        for(int i=0;i<charName.length;i++){
            charName[i]+=5;
        }
        return charName;
    }

    public void changeAdminLogInOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminLogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
