package controller;

import db.DbConnection;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class ReportDetailFormController {
    public void openOrderWiseIncomeReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/OrderwiseIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void openEmployeeDetailReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/EmployeeDetails.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void openMostMovableItemDetailReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/MostMovableMealType.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void openPieChartOfMostMovableItemList(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/MostMovableItemInChart.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void openDailyIncomeReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/DailyIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void openMonthlyIncomeReport(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/MonthlyIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
