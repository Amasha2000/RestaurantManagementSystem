package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import view.tm.EmployeeTM;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeDetailFormController implements Initializable {
    public JFXButton btnAddEmployee;
    public JFXButton btnUpdateEmployee;
    public TableColumn colEmployeeId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colPhoneNumber;
    public TableColumn colNIC;
    public TableColumn colDescription;
    public TableColumn colDelete;
    public JFXTextField txtEmployeeId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtSearch;
    public JFXTextField txtPhoneNumber;
    public JFXTextField txtNIC;
    public TableView<EmployeeTM> tblEmployee;
    public JFXRadioButton rdBtnCashier;
    public JFXRadioButton rdBtnChef;
    public JFXRadioButton rdBtnWaiter;
    public ToggleGroup toggleDescGroup;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern empIdPattern = Pattern.compile("^[E][-]([0|1][1-9]|10)$");
    Pattern empNamePattern = Pattern.compile("^[[A-z]+(?[\\s.]+[A-z]+)*]{10,45}$");
    Pattern empAddressPattern = Pattern.compile("^[[0-9A-z'\\.\\\\\\-\\s\\,]+]{10,60}$");
    Pattern empNicPattern = Pattern.compile("[[0-9][V]?[X]?]{10,12}");
    Pattern empPhoneNumberPattern = Pattern.compile("^[0-9]{3}[-][0-9]{7}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddEmployee.setDisable(true);
        btnUpdateEmployee.setDisable(true);
        validateInit();
        try {
            setEmployeeDetail();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void validateInit() {
        map.put(txtEmployeeId, empIdPattern);
        map.put(txtName, empNamePattern);
        map.put(txtAddress, empAddressPattern);
        map.put(txtPhoneNumber, empPhoneNumberPattern);
        map.put(txtNIC, empNicPattern);

    }

    private void setEmployeeDetail() throws SQLException, ClassNotFoundException {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmployeeId.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colName.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colAddress.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colNIC.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colNIC.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("empPhoneNumber"));
        colPhoneNumber.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("empDescription"));
        colDescription.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colDelete.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        setEmployeeDetailToTable(new ManageEmployeeController().getAllEmployees());

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadEmployeeDetail(newValue);
        });
    }

    private void loadEmployeeDetail(EmployeeTM tm) {
        try {
            txtEmployeeId.setText(tm.getEmpId());
            txtName.setText(tm.getEmpName());
            txtAddress.setText(tm.getEmpAddress());
            txtNIC.setText(tm.getEmpNic());
            txtPhoneNumber.setText(tm.getEmpPhoneNumber());
            if (tm.getEmpDescription().equals("Cashier")) {
                rdBtnCashier.setSelected(true);
            } else if (tm.getEmpDescription().equals("Chef")) {
                rdBtnChef.setSelected(true);
            } else if (tm.getEmpDescription().equals("Waiter")) {
                rdBtnWaiter.setSelected(true);
            }
        } catch (NullPointerException e) {

        }
        txtEmployeeId.setEditable(false);
        txtNIC.setEditable(false);
    }

    private void setEmployeeDetailToTable(ArrayList<Employee> allEmployees) {
        ObservableList<EmployeeTM> employeeList = FXCollections.observableArrayList();
        allEmployees.forEach(e -> {
            Button btn = new Button("Delete");
            btn.setStyle("-fx-background-color:#81ecec");
            employeeList.add(new EmployeeTM(e.getEmpId(), e.getEmpName(), e.getEmpAddress(), e.getEmpNic(), e.getEmpPhoneNumber(), e.getEmpDescription(), btn));
            btn.setOnAction((event -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this employee? 🤔", yes, no);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(yes) == no) {

                } else {
                    try {
                        if (new ManageEmployeeController().deleteEmployee(e.getEmpId())) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee is Deleted 👍").show();
                            setEmployeeDetail();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }));
        });
        tblEmployee.setItems(employeeList);
    }


    public void tableCodeKeyReleased(KeyEvent keyEvent) {
        Object response = validateFields(map, btnAddEmployee);
        if (response instanceof TextField) {
            TextField errorText = (TextField) response;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnUpdateEmployee.setDisable(true);
        } else if (response instanceof Boolean) {
                btnAddEmployee.setDisable(false);
                btnUpdateEmployee.setDisable(false);
        }
    }

    private Object validateFields(LinkedHashMap<TextField, Pattern> map, JFXButton btnAddEmployee) {
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: red;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
                    ((AnchorPane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;" + "-fx-background-color: white;");
                }
                btnAddEmployee.setDisable(true);
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
            ((AnchorPane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");
        }
            return true;
    }



    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (!empIdPattern.matcher(txtSearch.getText()).matches()) {
            if (!txtSearch.getText().isEmpty()) {
                txtSearch.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
           }
        }else {
             txtSearch.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
             ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String employeeId = txtSearch.getText();
                    ArrayList<Employee> e1 = new ManageEmployeeController().getEmployee(employeeId);
                    if (e1 == null) {
                        new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                    } else {
                        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
                        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
                        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
                        colNIC.setCellValueFactory(new PropertyValueFactory<>("empNic"));
                        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("empPhoneNumber"));
                        colDescription.setCellValueFactory(new PropertyValueFactory<>("empDescription"));
                        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
                        setEmployeeDetailToTable(e1);
                    }
                }

            }
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    try {
                        setEmployeeDetail();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
    }






    public void addEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RadioButton rdb= (RadioButton) toggleDescGroup.getSelectedToggle();
        try {
            Employee employee = new Employee(
                    txtEmployeeId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtNIC.getText(),
                    txtPhoneNumber.getText(),
                    rdb.getText()

            );
            if(new ManageEmployeeController().saveEmployee(employee)){
                new Alert(Alert.AlertType.CONFIRMATION, "New Employee is Added 👌").show();
                setEmployeeDetail();
                txtEmployeeId.clear();
                txtName.clear();
                txtAddress.clear();
                txtNIC.clear();
                txtPhoneNumber.clear();
                toggleDescGroup.getSelectedToggle().setSelected(false);
                btnAddEmployee.setDisable(true);
                btnUpdateEmployee.setDisable(true);
            }else{
                new Alert(Alert.AlertType.WARNING, "Employee Id Already Exists 😑").show();
            }
        }catch(NullPointerException e){
            new Alert(Alert.AlertType.WARNING, "Select Employee Type to save 😑").show();
        }
    }

    public void updateEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RadioButton rdb= (RadioButton) toggleDescGroup.getSelectedToggle();
        try {
            Employee e1 = new Employee(
                    txtEmployeeId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtNIC.getText(),
                    txtPhoneNumber.getText(),
                    rdb.getText()
            );
        if(new ManageEmployeeController().updateEmployee(e1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Table Updated 👌").show();
            setEmployeeDetail();
            txtEmployeeId.clear();
            txtName.clear();
            txtAddress.clear();
            txtNIC.clear();
            txtPhoneNumber.clear();
            toggleDescGroup.getSelectedToggle().setSelected(false);
            btnAddEmployee.setDisable(true);
            btnUpdateEmployee.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Add Employee Details To Update 😑").show();
        }
        }catch(NullPointerException e){
            new Alert(Alert.AlertType.WARNING," Please select employee type and add to update").show();
        }
    }
}
