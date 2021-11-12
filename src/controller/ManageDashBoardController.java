package controller;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageDashBoardController {
    public double getTotalIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(cost) FROM Orders");
        ResultSet rst= stm.executeQuery();
        while (rst.next()){
            return rst.getDouble(1);
        }
        return 0.0;
    }


public double getDailyIncome() throws SQLException, ClassNotFoundException {
    PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(cost) FROM Orders WHERE YEAR(date)=YEAR(NOW()) AND MONTH(date)=MONTH(NOW()) AND DAY(date)=DAY(NOW())");
    ResultSet rst= stm.executeQuery();
    while (rst.next()){
        return rst.getDouble(1);
    }
    return 0.0;
}

public int getEmployeeCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(empId) FROM employee");
        ResultSet rst= stm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
}

public int getTableCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(tId) FROM Tabless");
        ResultSet rst= stm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
}

public int getMealTypeCount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(subCategories) FROM meal");
        ResultSet rst= stm.executeQuery();
        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
}
}