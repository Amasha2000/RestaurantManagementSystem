package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Meal;
import model.OrderDetail;
import model.Orders;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public AnchorPane cashierDashBoardContext;
    public Label lblTime;
    public Label lblDate;
    public JFXComboBox cmbRice;
    public JFXComboBox cmbTableCode;
    public Spinner spinnerQuantity;
    public JFXTextField txtPrice;
    public TableView<ItemTM> tblDetail;
    public TableColumn colItem;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public JFXButton btnAddOrder;
    public Label lblTotal;
    public Label lblOrderId;
    public JFXButton btnPlaceOrder;
    public JFXComboBox cmbSoup;
    public JFXComboBox cmbSeaFood;
    public JFXComboBox cmbMeat;
    public JFXComboBox cmbFreshJuice;
    public JFXComboBox cmbDesserts;
    public JFXComboBox cmbPizza;
    public JFXComboBox cmbSandwich;
    public JFXComboBox cmbNoodles;
    public JFXComboBox cmbBurger;
    public JFXComboBox cmbHotDrink;
    public JFXComboBox cmbSoftDrink;
    public JFXButton btnCheckOut;
    public JFXButton btnPlaceOrders;

    int selectedRowForRemove = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        setOrderId();
        btnPlaceOrder.setDisable(true);
        txtPrice.setEditable(false);

        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colItem.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:17px;");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:17px;");
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:17px;");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colTotal.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:17px;");

        SpinnerValueFactory<Integer> quantity = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        spinnerQuantity.setStyle("-fx-alignment:CENTER;"+"-fx-font-size:17px;");
        spinnerQuantity.setValueFactory(quantity);
        btnAddOrder.setDisable(true);


        try {
            List<String> tableCodes = new ManageTableController().getTableCodes();
            cmbTableCode.getItems().addAll(tableCodes);

            List<String> riceCategories = new ManageMealController().getRiceCategories();
            cmbRice.getItems().addAll(riceCategories);

            List<String> soupCategories = new ManageMealController().getSoupCategories();
            cmbSoup.getItems().addAll(soupCategories);

            List<String> noodlesCategories = new ManageMealController().getNoodlesCategories();
            cmbNoodles.getItems().addAll(noodlesCategories);

            List<String> meatCategories = new ManageMealController().getMeatCategories();
            cmbMeat.getItems().addAll(meatCategories);

            List<String> seaFoodCategories = new ManageMealController().getSeaFoodCategories();
            cmbSeaFood.getItems().addAll(seaFoodCategories);

            List<String> dessertCategories = new ManageMealController().getDessertCategories();
            cmbDesserts.getItems().addAll(dessertCategories);

            List<String> pizzaCategories = new ManageMealController().getPizzaCategories();
            cmbPizza.getItems().addAll(pizzaCategories);

            List<String> sandwichCategories = new ManageMealController().getSandwichCategories();
            cmbSandwich.getItems().addAll(sandwichCategories);

            List<String> burgerCategories = new ManageMealController().getBurgerCategories();
            cmbBurger.getItems().addAll(burgerCategories);

            List<String> softDrinkCategories = new ManageMealController().getSoftDrinkCategories();
            cmbSoftDrink.getItems().addAll(softDrinkCategories);

            List<String> hotDrinkCategories = new ManageMealController().getHotDrinkCategories();
            cmbHotDrink.getItems().addAll(hotDrinkCategories);

            List<String> freshJuiceCategories = new ManageMealController().getFreshJuiceCategories();
            cmbFreshJuice.getItems().addAll(freshJuiceCategories);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbRice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbSoup.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbSoup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbNoodles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbSoup.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbMeat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbSoup.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbSeaFood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSoup.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbDesserts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbSoup.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbPizza.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbSoup.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbSandwich.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSoup.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbBurger.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbSoup.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbSoftDrink.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoup.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbHotDrink.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbSoup.setDisable(true);
                cmbFreshJuice.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbFreshJuice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setUnitPrice(newValue);
                cmbRice.setDisable(true);
                cmbNoodles.setDisable(true);
                cmbMeat.setDisable(true);
                cmbSeaFood.setDisable(true);
                cmbDesserts.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSandwich.setDisable(true);
                cmbBurger.setDisable(true);
                cmbSoftDrink.setDisable(true);
                cmbHotDrink.setDisable(true);
                cmbSoup.setDisable(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblDetail.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowForRemove = (int) newValue;
        });


        cmbTableCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (new ManageOrderController().isTableExists((String) cmbTableCode.getSelectionModel().getSelectedItem())) {
                } else if (cmbTableCode.getValue() != null && cmbTableCode.getValue().toString().equals(newValue)) {
                    new Alert(Alert.AlertType.WARNING, "Table is not available in this moment").show();
                    Platform.runLater(()-> {
                        cmbTableCode.getSelectionModel().clearSelection();
                    } );

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    private void setOrderId() {
        try {
            lblOrderId.setText(new ManageOrderController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUnitPrice(Object subCategory) throws SQLException, ClassNotFoundException {
        Meal m=new ManageMealController().getMeals((String) subCategory);
        if(m==null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set");
        }else{
           txtPrice.setText(String.valueOf(m.getUnitPrice()));
               btnAddOrder.setDisable(false);

        }
    }


    private void loadDateAndTime() {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time=new Timeline(new KeyFrame(Duration.ZERO,e->{
            LocalTime currentTime=LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+":"+currentTime.getMinute()+":"+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        btnPlaceOrders.setStyle("-fx-text-fill:white");
        btnCheckOut.setStyle("-fx-text-fill:#3d1a53");
        Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"))));
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
    }

    public void orderDetailOnAction(ActionEvent actionEvent) throws IOException {
        btnCheckOut.setStyle("-fx-text-fill:white");
        btnPlaceOrders.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/OrderDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashBoardContext.getChildren().clear();
        cashierDashBoardContext.getChildren().add(load);
    }

    ObservableList<ItemTM> obList= FXCollections.observableArrayList();
    public void addToOrderOnAction(ActionEvent actionEvent) {
        if(!cmbTableCode.getSelectionModel().isEmpty()) {
            String item = null;
            try {
                if (!(cmbRice.getSelectionModel().getSelectedItem().toString() == null)) {
                    item = cmbRice.getSelectionModel().getSelectedItem().toString();
                    cmbRice.valueProperty().set(null);
                    cmbSoup.setDisable(false);
                    cmbNoodles.setDisable(false);
                    cmbMeat.setDisable(false);
                    cmbSeaFood.setDisable(false);
                    cmbDesserts.setDisable(false);
                    cmbPizza.setDisable(false);
                    cmbSandwich.setDisable(false);
                    cmbBurger.setDisable(false);
                    cmbSoftDrink.setDisable(false);
                    cmbHotDrink.setDisable(false);
                    cmbFreshJuice.setDisable(false);
                }
            } catch (NullPointerException e) {
                try {
                    if (!(cmbSoup.getSelectionModel().getSelectedItem().toString() == null)) {
                        item = cmbSoup.getSelectionModel().getSelectedItem().toString();
                        cmbSoup.valueProperty().set(null);
                        cmbRice.setDisable(false);
                        cmbNoodles.setDisable(false);
                        cmbMeat.setDisable(false);
                        cmbSeaFood.setDisable(false);
                        cmbDesserts.setDisable(false);
                        cmbPizza.setDisable(false);
                        cmbSandwich.setDisable(false);
                        cmbBurger.setDisable(false);
                        cmbSoftDrink.setDisable(false);
                        cmbHotDrink.setDisable(false);
                        cmbFreshJuice.setDisable(false);
                    }
                } catch (NullPointerException e1) {
                    try {
                        if (!(cmbNoodles.getSelectionModel().getSelectedItem().toString() == null)) {
                            item = cmbNoodles.getSelectionModel().getSelectedItem().toString();
                            cmbNoodles.valueProperty().set(null);
                            cmbRice.setDisable(false);
                            cmbSoup.setDisable(false);
                            cmbMeat.setDisable(false);
                            cmbSeaFood.setDisable(false);
                            cmbDesserts.setDisable(false);
                            cmbPizza.setDisable(false);
                            cmbSandwich.setDisable(false);
                            cmbBurger.setDisable(false);
                            cmbSoftDrink.setDisable(false);
                            cmbHotDrink.setDisable(false);
                            cmbFreshJuice.setDisable(false);
                        }
                    } catch (NullPointerException e2) {
                        try {
                            if (!(cmbMeat.getSelectionModel().getSelectedItem().toString() == null)) {
                                item = cmbMeat.getSelectionModel().getSelectedItem().toString();
                                cmbMeat.valueProperty().set(null);
                                cmbRice.setDisable(false);
                                cmbNoodles.setDisable(false);
                                cmbSoup.setDisable(false);
                                cmbSeaFood.setDisable(false);
                                cmbDesserts.setDisable(false);
                                cmbPizza.setDisable(false);
                                cmbSandwich.setDisable(false);
                                cmbBurger.setDisable(false);
                                cmbSoftDrink.setDisable(false);
                                cmbHotDrink.setDisable(false);
                                cmbFreshJuice.setDisable(false);
                            }
                        } catch (NullPointerException e3) {
                            try {
                                if (!(cmbSeaFood.getSelectionModel().getSelectedItem().toString() == null)) {
                                    item = cmbSeaFood.getSelectionModel().getSelectedItem().toString();
                                    cmbSeaFood.valueProperty().set(null);
                                    cmbRice.setDisable(false);
                                    cmbNoodles.setDisable(false);
                                    cmbMeat.setDisable(false);
                                    cmbSoup.setDisable(false);
                                    cmbDesserts.setDisable(false);
                                    cmbPizza.setDisable(false);
                                    cmbSandwich.setDisable(false);
                                    cmbBurger.setDisable(false);
                                    cmbSoftDrink.setDisable(false);
                                    cmbHotDrink.setDisable(false);
                                    cmbFreshJuice.setDisable(false);
                                }
                            } catch (NullPointerException e4) {
                                try {
                                    if (!(cmbDesserts.getSelectionModel().getSelectedItem().toString() == null)) {
                                        item = cmbDesserts.getSelectionModel().getSelectedItem().toString();
                                        cmbDesserts.valueProperty().set(null);
                                        cmbRice.setDisable(false);
                                        cmbNoodles.setDisable(false);
                                        cmbMeat.setDisable(false);
                                        cmbSeaFood.setDisable(false);
                                        cmbSoup.setDisable(false);
                                        cmbPizza.setDisable(false);
                                        cmbSandwich.setDisable(false);
                                        cmbBurger.setDisable(false);
                                        cmbSoftDrink.setDisable(false);
                                        cmbHotDrink.setDisable(false);
                                        cmbFreshJuice.setDisable(false);
                                    }
                                } catch (NullPointerException e5) {
                                    try {
                                        if (!(cmbPizza.getSelectionModel().getSelectedItem().toString() == null)) {
                                            item = cmbPizza.getSelectionModel().getSelectedItem().toString();
                                            cmbPizza.valueProperty().set(null);
                                            cmbRice.setDisable(false);
                                            cmbNoodles.setDisable(false);
                                            cmbMeat.setDisable(false);
                                            cmbSeaFood.setDisable(false);
                                            cmbDesserts.setDisable(false);
                                            cmbSoup.setDisable(false);
                                            cmbSandwich.setDisable(false);
                                            cmbBurger.setDisable(false);
                                            cmbSoftDrink.setDisable(false);
                                            cmbHotDrink.setDisable(false);
                                            cmbFreshJuice.setDisable(false);
                                        }
                                    } catch (NullPointerException e6) {
                                        try {
                                            if (!(cmbSandwich.getSelectionModel().getSelectedItem().toString() == null)) {
                                                item = cmbSandwich.getSelectionModel().getSelectedItem().toString();
                                                cmbSandwich.valueProperty().set(null);
                                                cmbRice.setDisable(false);
                                                cmbNoodles.setDisable(false);
                                                cmbMeat.setDisable(false);
                                                cmbSeaFood.setDisable(false);
                                                cmbDesserts.setDisable(false);
                                                cmbPizza.setDisable(false);
                                                cmbSoup.setDisable(false);
                                                cmbBurger.setDisable(false);
                                                cmbSoftDrink.setDisable(false);
                                                cmbHotDrink.setDisable(false);
                                                cmbFreshJuice.setDisable(false);
                                            }
                                        } catch (NullPointerException e7) {
                                            try {
                                                if (!(cmbBurger.getSelectionModel().getSelectedItem().toString() == null)) {
                                                    item = cmbBurger.getSelectionModel().getSelectedItem().toString();
                                                    cmbBurger.valueProperty().set(null);
                                                    cmbRice.setDisable(false);
                                                    cmbNoodles.setDisable(false);
                                                    cmbMeat.setDisable(false);
                                                    cmbSeaFood.setDisable(false);
                                                    cmbDesserts.setDisable(false);
                                                    cmbPizza.setDisable(false);
                                                    cmbSandwich.setDisable(false);
                                                    cmbSoup.setDisable(false);
                                                    cmbSoftDrink.setDisable(false);
                                                    cmbHotDrink.setDisable(false);
                                                    cmbFreshJuice.setDisable(false);
                                                }
                                            } catch (NullPointerException e8) {
                                                try {
                                                    if (!(cmbSoftDrink.getSelectionModel().getSelectedItem().toString() == null)) {
                                                        item = cmbSoftDrink.getSelectionModel().getSelectedItem().toString();
                                                        cmbSoftDrink.valueProperty().set(null);
                                                        cmbRice.setDisable(false);
                                                        cmbNoodles.setDisable(false);
                                                        cmbMeat.setDisable(false);
                                                        cmbSeaFood.setDisable(false);
                                                        cmbDesserts.setDisable(false);
                                                        cmbPizza.setDisable(false);
                                                        cmbSandwich.setDisable(false);
                                                        cmbBurger.setDisable(false);
                                                        cmbSoup.setDisable(false);
                                                        cmbHotDrink.setDisable(false);
                                                        cmbFreshJuice.setDisable(false);
                                                    }
                                                } catch (NullPointerException e9) {
                                                    try {
                                                        if (!(cmbHotDrink.getSelectionModel().getSelectedItem().toString() == null)) {
                                                            item = cmbHotDrink.getSelectionModel().getSelectedItem().toString();
                                                            cmbHotDrink.valueProperty().set(null);
                                                            cmbRice.setDisable(false);
                                                            cmbNoodles.setDisable(false);
                                                            cmbMeat.setDisable(false);
                                                            cmbSeaFood.setDisable(false);
                                                            cmbDesserts.setDisable(false);
                                                            cmbPizza.setDisable(false);
                                                            cmbSandwich.setDisable(false);
                                                            cmbBurger.setDisable(false);
                                                            cmbSoftDrink.setDisable(false);
                                                            cmbSoup.setDisable(false);
                                                            cmbFreshJuice.setDisable(false);
                                                        }
                                                    } catch (NullPointerException e10) {
                                                        try {
                                                            if (!(cmbFreshJuice.getSelectionModel().getSelectedItem().toString() == null)) {
                                                                item = cmbFreshJuice.getSelectionModel().getSelectedItem().toString();
                                                                cmbFreshJuice.valueProperty().set(null);
                                                                cmbRice.setDisable(false);
                                                                cmbNoodles.setDisable(false);
                                                                cmbMeat.setDisable(false);
                                                                cmbSeaFood.setDisable(false);
                                                                cmbDesserts.setDisable(false);
                                                                cmbPizza.setDisable(false);
                                                                cmbSandwich.setDisable(false);
                                                                cmbBurger.setDisable(false);
                                                                cmbSoftDrink.setDisable(false);
                                                                cmbHotDrink.setDisable(false);
                                                                cmbSoup.setDisable(false);
                                                            }
                                                        } catch (NullPointerException e11) {

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }


            int quantity = (int) spinnerQuantity.getValue();
            double unitPrice = Double.parseDouble(txtPrice.getText());
            double total = Double.parseDouble(String.format("%.2f",quantity * unitPrice));
            ItemTM tm = new ItemTM(
                    item,
                    quantity,
                    unitPrice,
                    total
            );

            int rowNumber = isExists(tm);
            if (isExists(tm) == -1) {
                obList.add(tm);
            } else {
                ItemTM temp = obList.get(rowNumber);
                ItemTM newTm = new ItemTM(
                        temp.getItem(),
                        temp.getQuantity() + quantity,
                        unitPrice,
                        temp.getTotal() + total
                );
                obList.remove(rowNumber);
                obList.add(newTm);

            }
            tblDetail.setItems(obList);
            btnPlaceOrder.setDisable(false);
            spinnerQuantity.getValueFactory().setValue(1);
            txtPrice.clear();
            calculateCost();
            btnAddOrder.setDisable(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select a Table to Add the Order ").show();
        }
    }
    
    private int  isExists(ItemTM tm){
        for(int i=0;i<obList.size();i++){
            if(tm.getItem().equals(obList.get(i).getItem())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double total=0;
        for(ItemTM tm:obList){
         total=total+tm.getTotal();
        }
        lblTotal.setText(total+"/=");
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if(selectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING,"Please select a row").show();
        }else{
            obList.remove(selectedRowForRemove);
            calculateCost();
            tblDetail.refresh();
            tblDetail.getSelectionModel().clearSelection();
        }
    }

    public void placeOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        double ttl=0;
        for(ItemTM temp:obList){
            ttl+=temp.getTotal();
            orderDetails.add(new OrderDetail(
                    temp.getItem(),
                    temp.getQuantity(),
                    temp.getUnitPrice()
            ));
        }
        Orders order=new Orders(
                lblOrderId.getText(),
                cmbTableCode.getSelectionModel().getSelectedItem().toString(),
                lblDate.getText(),
                lblTime.getText(),
                ttl,
                orderDetails
        );


            if (new ManageOrderController().placeOrder(order) && new ManageOrderController().saveOrderCopy(order)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully").show();
                tblDetail.getItems().clear();
                cmbTableCode.valueProperty().setValue(null);
                lblTotal.setText("0.00");
                btnAddOrder.setDisable(true);
                btnPlaceOrder.setDisable(true);
                setOrderId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
    }
}
