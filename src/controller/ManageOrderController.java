package controller;

import db.DbConnection;
import model.OrderDetail;
import model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderController {

   public boolean isTableExists(String tableCode) throws SQLException, ClassNotFoundException {
     PreparedStatement stm = DbConnection.getInstance()
            .getConnection().prepareStatement("SELECT tblCode FROM OrderCopy WHERE tblCode=?");
             stm.setObject(1,tableCode);
             ResultSet rst=stm.executeQuery();
            if (rst.next()) {
                return false;
            }
            return true;
   }


        public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT oId FROM Orders ORDER BY oId DESC LIMIT 1").executeQuery();
        if(rst.next()){
            int temp= Integer.parseInt(rst.getString(1).split("-")[1]);
            temp=temp+1;
            if(temp<=9){
                return "O-00"+temp;
            }else if(temp<=99){
                return "O-0"+temp;
            }else{
                return "O-"+temp;
            }
        }else{
            return "O-001";
        }
    }

      public boolean saveOrderCopy(Orders orders) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO OrderCopy(orderId,tblCode,date,time,cost) VALUES (?,?,?,?,?)");
        stm.setObject(1, orders.getOrderId());
        stm.setObject(2, orders.getTblCode());
        stm.setObject(3, orders.getOrderDate());
        stm.setObject(4, orders.getOrderTime());
        stm.setObject(5, orders.getCost());
        if(stm.executeUpdate()>0){
            return true;
        }
        return false;
    }

    public boolean placeOrder(Orders orders) throws SQLException, ClassNotFoundException {
           Connection con=null;
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Orders(oId,tblCode,date,time,cost) VALUES (?,?,?,?,?)");
            stm.setObject(1, orders.getOrderId());
            stm.setObject(2, orders.getTblCode());
            stm.setObject(3, orders.getOrderDate());
            stm.setObject(4, orders.getOrderTime());
            stm.setObject(5, orders.getCost());

            try{
            if (stm.executeUpdate() > 0) {
                if (saveOrderDetail(orders.getOrderId(), orders.getOrderDetails())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        return false;
    }

    private boolean saveOrderDetail(String orderId, ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetail temp:orderDetails){
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO OrderDetail(code,mealType,quantity,price) VALUES (?,?,?,?)");
            stm.setObject(1,orderId);
            stm.setObject(2,temp.getItem());
            stm.setObject(3,temp.getQuantity());
            stm.setObject(4,temp.getUnitPrice());
            if(stm.executeUpdate()>0){

            }else{
                return false;
            }
        }

        return true;
    }

    public String getOrderIds(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Orders WHERE oId='"+oId+"'").executeQuery();
        String orderValue=null;
        while (rst.next()){
                   orderValue= rst.getString(1);

        }
         return orderValue;
    }

    public String getTableCodes(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Orders WHERE oId='"+oId+"'").executeQuery();
        String tableValue=null;
        while (rst.next()){
           tableValue= rst.getString(2);

        }
        return tableValue;
    }

    public String getDate(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Orders WHERE oId='"+oId+"'").executeQuery();
        String date=null;
        while (rst.next()){
            date= rst.getString(3);

        }
        return date;
    }

    public String getTime(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Orders WHERE oId='"+oId+"'").executeQuery();
        String time=null;
        while (rst.next()){
            time= rst.getString(4);

        }
        return time;
    }

    public double getCost(String oId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Orders WHERE oId='"+oId+"'").executeQuery();
        double cost=0;
        while (rst.next()){
            cost= rst.getDouble(5);

        }
        return cost;
    }
    public ArrayList<Orders> getOrderDetailsToTable() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT oId,tblCode,date,time,cost FROM Orders");
        ResultSet rst=stm.executeQuery();
        ArrayList<Orders> order=new ArrayList<>();
        while (rst.next()){
            order.add(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
        }
        return order;
    }

    public ArrayList<Orders> getDetail(String orderId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT oId,tblCode,date,time,cost FROM Orders WHERE oId='"+orderId+"'");
        ResultSet rst=stm.executeQuery();
        ArrayList<Orders> searchOrders= new ArrayList<>();
        if(rst.next()){
            searchOrders.add(new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            ));
            return searchOrders;
        }else{
            return null;
        }
    }
}
