package model;


public class Meal {
     private String categories;
     private String subCategories;
     private double unitPrice;

    public Meal() {
    }

    public Meal(String categories, String subCategories, double unitPrice) {
        this.setCategories(categories);
        this.setSubCategories(subCategories);
        this.setUnitPrice(unitPrice);
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
}
