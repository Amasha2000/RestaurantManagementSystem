package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetail;
import view.tm.OrderDetailTM;
import view.tm.OrderTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemTableFormController implements Initializable {
    public TableView<OrderDetailTM> tblItem;
    public TableColumn colItemName;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item"));
        colItemName.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");

        }

    public void allData(String id) {
        try {
            ObservableList<OrderDetailTM> list= FXCollections.observableArrayList();
            for(OrderDetail temp:new ManageOrderDetailController().getAllOrderDetailsForBill(id)){
                list.add(new OrderDetailTM(temp.getItem(),temp.getQuantity(),temp.getUnitPrice()));
            }
            tblItem.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
