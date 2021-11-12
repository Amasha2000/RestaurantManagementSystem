package view.tm;

import javafx.scene.control.Button;

public class OrderCopyTM {
    private String orderId;
    private String tblCode;
    private String date;
    private String time;
    private Double cost;
    private Button btn;

    public OrderCopyTM() {
    }

    public OrderCopyTM(String orderId, String tblCode, String date, String time, Double cost, Button btn) {
        this.setOrderId(orderId);
        this.setTblCode(tblCode);
        this.setDate(date);
        this.setTime(time);
        this.setCost(cost);
        this.setBtn(btn);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
