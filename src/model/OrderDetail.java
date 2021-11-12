package model;

public class OrderDetail {
    private String item;
    private int quantity;
    private double unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(String item, int quantity, double unitPrice) {
        this.setItem(item);
        this.setQuantity(quantity);
        this.setUnitPrice(unitPrice);
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
