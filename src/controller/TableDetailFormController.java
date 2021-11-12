package controller;

import com.jfoenix.controls.JFXButton;
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
import model.Table;
import util.Validation;
import view.tm.TableTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class TableDetailFormController implements Initializable {
    public JFXTextField txtTableCode;
    public JFXButton btnAddTable;
    public JFXTextField txtTableNumber;
    public JFXTextField txtNumberOfSeats;
    public TableView<TableTM> tblTable;
    public TableColumn colTableNumber;
    public TableColumn colTableCode;
    public TableColumn colNumberOfSeats;
    public TableColumn colDelete;
    public JFXButton btnUpdateTable;
    public JFXTextField txtSearch;

    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap<>();
    Pattern tableNumberPattern=Pattern.compile("^(1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20)$");
    Pattern tableCodePattern=Pattern.compile("^[H][-]([0|1][1-9]|10)[E]?$");
    Pattern numberOfSeatsPattern=Pattern.compile("^(2|4|6|8)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdateTable.setDisable(true);
        btnAddTable.setDisable(true);
        validateInit();
        setTableDetail();


    }

    private void setTableDetail() {
        try {
            colTableNumber.setCellValueFactory(new PropertyValueFactory<>("table_number"));
            colTableNumber.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colTableCode.setCellValueFactory(new PropertyValueFactory<>("table_code"));
            colTableCode.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colNumberOfSeats.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
            colNumberOfSeats.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
            colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
            colDelete.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");

            setTableDetailToTable(new ManageTableController().getAllTables());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadTableData(newValue);
        });
    }

    private void loadTableData(TableTM tm) {
        try {
            txtTableNumber.setText(String.valueOf(tm.getTable_number()));
            txtTableCode.setText(tm.getTable_code());
            txtNumberOfSeats.setText(String.valueOf(tm.getNumberOfSeats()));
        }catch(NullPointerException e){
        }
        txtTableNumber.setEditable(false);
        txtTableCode.setEditable(false);
    }

    private void setTableDetailToTable(ArrayList<Table> allTables) {
        ObservableList<TableTM> tableList=FXCollections.observableArrayList();
        allTables.forEach(e->{
            Button btn=new Button("Delete");
            btn.setStyle("-fx-background-color:#81ecec");
            tableList.add(new TableTM(e.getTable_number(),e.getTable_code(),e.getNumberOfSeats(),btn));

                btn.setOnAction((event )->{
                ButtonType yes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
                ButtonType no=new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this table? 🤔",yes,no);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result=alert.showAndWait();
                if(result.orElse(yes)==no){

                }else {
                    try {
                        if(  new ManageTableController().deleteTable(e.getTable_number())){
                            new Alert(Alert.AlertType.CONFIRMATION, "Table is Deleted 👍").show();
                            setTableDetail();
                                }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            } );
        });

            tblTable.setItems(tableList);
    }

    private void validateInit() {
        map.put(txtTableNumber,tableNumberPattern);
        map.put(txtTableCode,tableCodePattern);
        map.put(txtNumberOfSeats,numberOfSeatsPattern);
    }


    public void addTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            Table table = new Table(
                    Integer.parseInt(txtTableNumber.getText()),
                    txtTableCode.getText(),
                    Integer.parseInt(txtNumberOfSeats.getText())
            );

            if (new ManageTableController().saveTable(table)) {
                new Alert(Alert.AlertType.CONFIRMATION, "New Table is Added 👌").show();
                setTableDetail();
                txtTableNumber.clear();
                txtTableCode.clear();
                txtNumberOfSeats.clear();
                btnAddTable.setDisable(true);
                btnUpdateTable.setDisable(true);
            } else {
                new Alert(Alert.AlertType.WARNING, "Table Number Or Table Code Already Exists 😑").show();
            }
         }

    public void tableCodeKeyReleased(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAddTable);

            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                if(keyEvent.getCode()==KeyCode.ENTER) {
                    errorText.requestFocus();
                }
                btnUpdateTable.setDisable(true);
            } else if (response instanceof Boolean) {
                btnUpdateTable.setDisable(false);
                }
           }


    public void updateTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Table t1=new Table(
                Integer.parseInt(txtTableNumber.getText()),
                txtTableCode.getText(),
                Integer.parseInt(txtNumberOfSeats.getText())
        );
        if(new ManageTableController().updateTable(t1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Table Updated 👌").show();
                setTableDetail();
                txtTableNumber.clear();
                txtTableCode.clear();
                txtNumberOfSeats.clear();
                txtTableNumber.setEditable(true);
                txtTableCode.setEditable(true);
                 btnAddTable.setDisable(true);
                 btnUpdateTable.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Add Table Details To Update 😑").show();
        }
    }

    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
            if (!tableNumberPattern.matcher(txtSearch.getText()).matches()) {
                if (!txtSearch.getText().isEmpty()) {
                    txtSearch.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                    ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
                }
            }else {
                txtSearch.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
                ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");
                try {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        int tableId = Integer.parseInt(txtSearch.getText());
                        ArrayList<Table> t1 = new ManageTableController().getTable(tableId);
                        if (t1 == null) {
                            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                        } else {
                            colTableNumber.setCellValueFactory(new PropertyValueFactory<>("table_number"));
                            colTableCode.setCellValueFactory(new PropertyValueFactory<>("table_code"));
                            colNumberOfSeats.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
                            colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
                            setTableDetailToTable(t1);
                        }
                    }

                } catch (NumberFormatException e) {

                }
                txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        setTableDetail();
                    }
                });
            }

        }
}
