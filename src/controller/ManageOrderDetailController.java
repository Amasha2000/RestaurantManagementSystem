package controller;

import db.DbConnection;
import model.OrderCopy;
import model.OrderDetail;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderDetailController {
    public ArrayList<OrderCopy> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT orderId,tblCode,date,time,cost FROM OrderCopy");
        ResultSet rst=stm.executeQuery();
        ArrayList<OrderCopy> orders=new ArrayList<>();
        while (rst.next()){
            orders.add(new OrderCopy(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return orders;
    }

    public boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM OrderCopy WHERE orderId='"+orderId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<OrderDetail> getAllOrderDetailsForBill(String orderId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT mealType,quantity,price FROM OrderDetail WHERE code='"+orderId+"'");
        ResultSet rst=stm.executeQuery();
        ArrayList<OrderDetail> orders=new ArrayList<>();
        while (rst.next()){
            orders.add(new OrderDetail(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getDouble(3)
            ));
        }
        return orders;
    }

    public ArrayList<OrderCopy> getDetail(String orderId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT orderId,tblCode,date,time,cost FROM OrderCopy WHERE orderId='"+orderId+"'");
        ResultSet rst=stm.executeQuery();
        ArrayList<OrderCopy> searchOrder= new ArrayList<>();
        if(rst.next()){
            searchOrder.add(new OrderCopy(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
            return searchOrder;
        }else{
            return null;
        }
    }
}
