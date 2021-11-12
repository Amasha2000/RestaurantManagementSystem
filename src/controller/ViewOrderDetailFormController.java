package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Orders;
import view.tm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ViewOrderDetailFormController implements Initializable {

    public TableView<OrderTM> tblOrderDetail;
    public TableColumn colOrderId;
    public TableColumn colTableCode;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;
    public JFXTextField txtSearch;


    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap<>();
    Pattern orderIdPattern=Pattern.compile("^[O][-][0-9]{3,}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateInit();
        try {
            setDetailToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void validateInit() {
        map.put(txtSearch,orderIdPattern);
    }

    private void setDetailToTable() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderId.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colTableCode.setCellValueFactory(new PropertyValueFactory<>("tblCode"));
        colTableCode.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTime.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCost.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");


        setOrderDetailToTable(new ManageOrderController().getOrderDetailsToTable());

        tblOrderDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadItemDetails(newValue.getOrderId());
            } catch (Exception e) {

            }
        });
    }

    private void loadItemDetails(String orderId) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("../view/ItemTableForm.fxml"));
        Parent load = fxmlLoader.load();
        ItemTableFormController controller=fxmlLoader.getController();
        controller.allData(orderId);
        Stage stage=new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }



    private void setOrderDetailToTable(ArrayList<Orders> orderDetailsToTable) {
        ObservableList<OrderTM> orderList = FXCollections.observableArrayList();
        orderDetailsToTable.forEach(e -> {
            orderList.add(new OrderTM(e.getOrderId(), e.getTblCode(), e.getOrderDate(), e.getOrderTime(), e.getCost()));

        });
        tblOrderDetail.setItems(orderList);
    }

    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (!orderIdPattern.matcher(txtSearch.getText()).matches()) {
            if (!txtSearch.getText().isEmpty()) {
                txtSearch.getParent().setStyle("-fx-border-color: red;"+"-fx-border-width:2;"+"-fx-border-radius:8;");
                ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;"+"-fx-background-color: white;");
            }
        }else {
            txtSearch.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
            ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");
            try {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String orderId = txtSearch.getText();
                    ArrayList<Orders> o1 = new ManageOrderController().getDetail(orderId);
                    if (o1 == null) {
                        new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                    } else {
                        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                        colTableCode.setCellValueFactory(new PropertyValueFactory<>("tblCode"));
                        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
                        setOrderDetailToTable(o1);
                    }
                }

            } catch (NumberFormatException e) {

            }
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    try {
                        setDetailToTable();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
