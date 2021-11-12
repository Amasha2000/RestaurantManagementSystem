package controller;

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
import model.OrderCopy;
import model.OrderDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.ItemTM;
import view.tm.OrderCopyTM;
import view.tm.OrderDetailTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class OrderDetailFormController implements Initializable {

    public TableView tblOrderDetails;
    public TableColumn colOrderId;
    public TableColumn colTableCode;
    public TableColumn colTime;
    public TableColumn colDate;
    public TableColumn colTotalPrice;
    public TableColumn colCheckOut;
    public TableView tblItemDetails;
    public TableColumn colItemName;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public Label lblTableCode;
    public Label lblDate;
    public Label lblTime;
    public Label lblOrderId;
    public Label lblCost;
    public JFXTextField txtSearch;

    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap<>();
    Pattern orderIdPattern=Pattern.compile("^[O][-][0-9]{3,}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateInit();
        try {
            setOrderDetail();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblItemDetails.setPlaceholder(new Label(""));
    }

    private void validateInit() {
        map.put(txtSearch,orderIdPattern);
    }

    private void setOrderDetail() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderId.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colTableCode.setCellValueFactory(new PropertyValueFactory<>("tblCode"));
        colTableCode.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTime.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colTotalPrice.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colCheckOut.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");

        setOrderDetailToTable(new ManageOrderDetailController().getAllOrderDetails());

        colItemName.setCellValueFactory(new PropertyValueFactory<>("item"));
        colItemName.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPrice.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:18px;");

    }

    private void setOrderDetailToTable(ArrayList<OrderCopy> allOrderDetails) {
        ObservableList<OrderCopyTM> orderList= FXCollections.observableArrayList();
        allOrderDetails.forEach(e->{
            Button btn=new Button("Check Out");
            btn.setStyle("-fx-background-color:#81ecec");
            orderList.add(new OrderCopyTM(e.getOrderId(),e.getTblCode(),e.getOrderDate(),e.getOrderTime(),e.getCost(),btn));
            btn.setOnAction((event )->{
                try {
                    lblOrderId.setText(new ManageOrderController().getOrderIds(e.getOrderId()));
                    lblTableCode.setText(new ManageOrderController().getTableCodes(e.getOrderId()));
                    lblDate.setText(new ManageOrderController().getDate(e.getOrderId()));
                    lblTime.setText(new ManageOrderController().getTime(e.getOrderId()));
                    lblCost.setText(String.valueOf(new ManageOrderController().getCost(e.getOrderId())));
                    setOrderDetailsToTable(new ManageOrderDetailController().getAllOrderDetailsForBill(e.getOrderId()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    if(  new ManageOrderDetailController().deleteOrder(e.getOrderId())){
                        new Alert(Alert.AlertType.CONFIRMATION, "Order has Checked Out 👍").show();
                        setOrderDetail();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }

            } );
        });

        tblOrderDetails.setItems(orderList);
    }

    private void setOrderDetailsToTable(ArrayList<OrderDetail> allOrderDetailsForBill) {
        ObservableList<OrderDetailTM> orderList= FXCollections.observableArrayList();
        allOrderDetailsForBill.forEach(e-> {
            orderList.add(new OrderDetailTM(e.getItem(), e.getQuantity(), e.getUnitPrice()));
        });
        tblItemDetails.setItems(orderList);
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
                    ArrayList<OrderCopy> o1 = new ManageOrderDetailController().getDetail(orderId);
                    if (o1 == null) {
                        new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                    } else {
                        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                        colTableCode.setCellValueFactory(new PropertyValueFactory<>("tblCode"));
                        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("cost"));
                        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("btn"));
                        setOrderDetailToTable(o1);
                    }
                }

            } catch (NumberFormatException e) {

            }
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    try {
                        setOrderDetail();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public void printOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Bill.jrxml"));
            JasperReport compileReport= JasperCompileManager.compileReport(design);
            ObservableList<ItemTM> items = tblItemDetails.getItems();

            String orderId=lblOrderId.getText();
            String tableCode=lblTableCode.getText();
            String date=lblDate.getText();
            String time=lblTime.getText();
            String cost=lblCost.getText();

            HashMap map=new HashMap();
            map.put("orderId",orderId);
            map.put("tableCode",tableCode);
            map.put("date",date);
            map.put("time",time);
            map.put("cost",cost);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
