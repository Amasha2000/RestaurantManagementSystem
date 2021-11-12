package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import model.Meal;
import util.Validation;
import view.tm.MealTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MealDetailFormController implements Initializable {
    public JFXButton btnAddMeal;
    public JFXButton btnUpdateMeal;
    public TableView<MealTM> tblMeal;
    public TableColumn colCategories;
    public TableColumn colSubCategories;
    public TableColumn colUnitPrice;
    public TableColumn colDelete;
    public JFXComboBox cmbCategories;
    public JFXTextField txtSearch;
    public JFXTextField txtSubCategories;
    public JFXTextField txtUnitPrice;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern subCategoryPattern = Pattern.compile("^[A-z\\s]+[0-9]?[&]?$");
    Pattern unitPricePattern = Pattern.compile("^(0(?!\\.00)|[1-9]\\d{0,6})\\.\\d{2}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddMeal.setDisable(true);
        btnUpdateMeal.setDisable(true);
        cmbCategories.getItems().addAll("Fried Rice","Soup","Noodles","Meat","Sea Food","Dessert","Pizza","Sandwich","Burger","Soft Drink","Hot Drink","Fresh Juice");
        validateInit();
        try {
            setMealDetail();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void validateInit() {
        map.put(txtSubCategories, subCategoryPattern);
        map.put(txtUnitPrice, unitPricePattern);
    }

    private void setMealDetail() throws SQLException, ClassNotFoundException {
        colCategories.setCellValueFactory(new PropertyValueFactory<>("categories"));
        colCategories.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colSubCategories.setCellValueFactory(new PropertyValueFactory<>("subCategories"));
        colSubCategories.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colDelete.setStyle("-fx-alignment:CENTER;" + "-fx-font-size:18px;");

        setMealDetailToTable(new ManageMealController().getAllMeals());

        tblMeal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadMealData(newValue);
        });

    }

    private void loadMealData(MealTM tm) {
        try {
            cmbCategories.setValue(tm.getCategories());
            txtSubCategories.setText(tm.getSubCategories());
            NumberFormat formatter=new DecimalFormat("#0.00");
            txtUnitPrice.setText(String.valueOf(formatter.format(tm.getUnitPrice())));
        } catch (NullPointerException e) {
        }
    }

    private void setMealDetailToTable(ArrayList<Meal> allMeals) {
        ObservableList<MealTM> mealList = FXCollections.observableArrayList();
        allMeals.forEach(e -> {
            Button btn = new Button("Delete");
            btn.setStyle("-fx-background-color:#81ecec");
            mealList.add(new MealTM(e.getCategories(), e.getSubCategories(),e.getUnitPrice(), btn));

            System.out.println();
            btn.setOnAction((event) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure whether you want to delete this meal? 🤔", yes, no);
                alert.setTitle("Confirmation");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(yes) == no) {

                } else {
                    try {
                        if (new ManageMealController().deleteMeal(e.getSubCategories())) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Meal is Deleted 👍").show();
                            setMealDetail();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            });
        });

        tblMeal.setItems(mealList);

    }

    public void mealAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            Meal meal = new Meal(
                    cmbCategories.getSelectionModel().getSelectedItem().toString(),
                    txtSubCategories.getText(),
                    Double.parseDouble(txtUnitPrice.getText())
            );
            if (new ManageMealController().saveMeal(meal)) {
                new Alert(Alert.AlertType.CONFIRMATION, "New Meal is Added 👌").show();
                setMealDetail();
                cmbCategories.valueProperty().set(null);
                txtSubCategories.clear();
                txtUnitPrice.clear();
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            } else {
                new Alert(Alert.AlertType.WARNING, "Meal Already Exists 😑").show();
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.WARNING, "Fill All Fields To Add 😑").show();
        }
    }

    public void mealUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            Meal m1 = new Meal(
                    cmbCategories.getSelectionModel().getSelectedItem().toString(),
                    txtSubCategories.getText(),
                    Double.parseDouble(txtUnitPrice.getText())
            );
            if (new ManageMealController().updateMeal(m1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Meal Detail Updated 👌").show();
                setMealDetail();
                cmbCategories.getSelectionModel().clearSelection();
                txtSubCategories.clear();
                txtUnitPrice.clear();
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again 😑").show();
            }
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.WARNING,"Please select Meal Category and add to update").show();
        }
    }

    public void txtCodeKeyReleased(KeyEvent keyEvent) {
        Object response = Validation.validate(map, btnAddMeal);

        if (response instanceof TextField) {
            TextField errorText = (TextField) response;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                errorText.requestFocus();
            }
            btnUpdateMeal.setDisable(true);
        } else if (response instanceof Boolean) {
            btnUpdateMeal.setDisable(false);
        }
    }

    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (!subCategoryPattern.matcher(txtSearch.getText()).matches()) {
            if (!txtSearch.getText().isEmpty()) {
                txtSearch.getParent().setStyle("-fx-border-color: red;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
                ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: red;" + "-fx-background-color: white;");
            }
        } else {
            txtSearch.getParent().setStyle("-fx-border-color: green;" + "-fx-border-width:2;" + "-fx-border-radius:8;");
            ((AnchorPane) txtSearch.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green;" + "-fx-background-color: white;");
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String subCategory = txtSearch.getText();
                ArrayList<Meal> m1 = new ManageMealController().getMeal(subCategory);
                if (m1 == null) {
                    new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                } else {
                    colCategories.setCellValueFactory(new PropertyValueFactory<>("categories"));
                    colSubCategories.setCellValueFactory(new PropertyValueFactory<>("subCategories"));
                    colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                    colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
                    setMealDetailToTable(m1);
                }
            }

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    try {
                        setMealDetail();
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

