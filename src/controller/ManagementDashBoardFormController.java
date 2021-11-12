package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagementDashBoardFormController implements Initializable {
    public AnchorPane dashBoardContext;
    public JFXButton btnDashBoard;
    public JFXButton btnMealDetails;
    public JFXButton btnEmployeeDetails;
    public JFXButton btnTableDetails;
    public JFXButton btnUserDetails;
    public JFXButton btnOrderDetails;
    public JFXButton btnViewReport;
    public Label lblTotalIncome;
    public Label lblTodayIncome;
    public Label lblNumberOfEmployees;
    public Label lblNumberOfTables;
    public Label lblNumberOfFoodItems;

    public void openAddTableWindow(ActionEvent actionEvent) throws IOException {
        btnTableDetails.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/TableDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }


    public void mealDetailOnAction(ActionEvent actionEvent) throws IOException {
        btnMealDetails.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/MealDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    public void userDetailsOnAction(ActionEvent actionEvent) throws IOException {
        btnUserDetails.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/UserDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    public void employeeDetailOnAction(ActionEvent actionEvent) throws IOException {
        btnEmployeeDetails.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/EmployeeDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    public void dashBoardOnAction(ActionEvent actionEvent) throws IOException {
        btnDashBoard.setStyle("-fx-text-fill:white");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        Stage window = (Stage) dashBoardContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagementDashboardForm.fxml"))));
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashBoardContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
    }

    public void viewReportOnAction(ActionEvent actionEvent) throws IOException {
        btnViewReport.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnOrderDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/ReportDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    public void viewOrderDetailOnAction(ActionEvent actionEvent) throws IOException {
        btnOrderDetails.setStyle("-fx-text-fill:white");
        btnDashBoard.setStyle("-fx-text-fill:#3d1a53");
        btnUserDetails.setStyle("-fx-text-fill:#3d1a53");
        btnEmployeeDetails.setStyle("-fx-text-fill:#3d1a53");
        btnMealDetails.setStyle("-fx-text-fill:#3d1a53");
        btnViewReport.setStyle("-fx-text-fill:#3d1a53");
        btnTableDetails.setStyle("-fx-text-fill:#3d1a53");
        URL resource = getClass().getResource("../view/ViewOrderDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lblTotalIncome.setText(String.valueOf(new ManageDashBoardController().getTotalIncome()));
            lblTodayIncome.setText(String.valueOf(new ManageDashBoardController().getDailyIncome()));
            lblNumberOfEmployees.setText(String.valueOf(new ManageDashBoardController().getEmployeeCount()));
            lblNumberOfTables.setText(String.valueOf(new ManageDashBoardController().getTableCount()));
            lblNumberOfFoodItems.setText(String.valueOf(new ManageDashBoardController().getMealTypeCount()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
