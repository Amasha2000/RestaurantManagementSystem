package view.tm;

import javafx.scene.control.Button;


public class MealTM {
     private String categories;
     private String subCategories;
     private double unitPrice;
     private Button btn;

    public MealTM() {
    }

    public MealTM(String categories, String subCategories, double unitPrice, Button btn) {
        this.setCategories(categories);
        this.setSubCategories(subCategories);
        this.setUnitPrice(unitPrice);
        this.setBtn(btn);
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
