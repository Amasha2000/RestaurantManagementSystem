package view.tm;

public class ItemTM {
     private String item;
     private int quantity;
     private double unitPrice;
     private double total;

    public ItemTM() {
    }

    public ItemTM(String item, int quantity, double unitPrice, double total) {
        this.setItem(item);
        this.setQuantity(quantity);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
