package model;

import java.util.ArrayList;

public class OrderCopy {
         private String orderId;
         private String tblCode;
         private String orderDate;
         private String orderTime;
         private double cost;
         private ArrayList<OrderDetail> orderDetails;

    public OrderCopy() {
    }

    public OrderCopy(String orderId, String tblCode, String orderDate, String orderTime, double cost) {
        this.setOrderId(orderId);
        this.setTblCode(tblCode);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setOrderDetails(orderDetails);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTblCode() {
        return tblCode;
    }

    public void setTblCode(String tblCode) {
        this.tblCode = tblCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
