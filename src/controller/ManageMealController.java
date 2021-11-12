package controller;

import db.DbConnection;
import model.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageMealController implements ManageMeal{
    @Override
    public boolean saveMeal(Meal m) throws SQLException, ClassNotFoundException {
        try {
            Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO Meal(categories,subCategories,unitPrice) VALUES(?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, m.getCategories());
            stm.setObject(2, m.getSubCategories());
            stm.setObject(3, m.getUnitPrice());
            return stm.executeUpdate() > 0;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateMeal(Meal m) throws SQLException, ClassNotFoundException {
            try{
            PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Meal SET categories=?,unitPrice=? WHERE subCategories=?");
            stm.setObject(1,m.getCategories());
            stm.setObject(2,m.getUnitPrice());
            stm.setObject(3,m.getSubCategories());
            return stm.executeUpdate()>0;
        }catch (Exception e){
           return false;
            }
    }


    @Override
    public ArrayList<Meal> getAllMeals() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT categories,subCategories,unitPrice FROM Meal");
        ResultSet rst=stm.executeQuery();
        ArrayList<Meal> meals=new ArrayList<>();
        while (rst.next()){
            meals.add(new Meal(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
        }
        return meals;
    }

    @Override
    public boolean deleteMeal(String subCategory) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Meal WHERE subCategories='"+subCategory+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Meal> getMeal(String subCategory) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT categories,subCategories,unitPrice FROM Meal WHERE subCategories=?");
        stm.setObject(1,subCategory);
        ResultSet rst=stm.executeQuery();
        ArrayList<Meal> searchMeal= new ArrayList<>();
        if(rst.next()){
            searchMeal.add(new Meal(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
            return searchMeal;
        }else{
            return null;
        }
    }
    public Meal getMeals(String subCategory) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Meal WHERE subCategories=?");
        stm.setObject(1, subCategory);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Meal(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            );

        } else {
            return null;
        }
    }

    public List<String> getRiceCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Fried Rice'").executeQuery();
        List<String> riceCategories = new ArrayList<>();
        while (rst.next()){
            riceCategories.add(
                    rst.getString(2)
            );
        }
        return riceCategories;
    }


    public List<String> getSoupCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Soup'").executeQuery();
        List<String> soupCategories = new ArrayList<>();
        while (rst.next()){
            soupCategories.add(
                    rst.getString(2)
            );
        }
        return soupCategories ;
    }


    public List<String> getNoodlesCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Noodles'").executeQuery();
        List<String> noodleCategories = new ArrayList<>();
        while (rst.next()){
            noodleCategories.add(
                    rst.getString(2)
            );
        }
        return noodleCategories ;
    }


    public List<String> getMeatCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Meat'").executeQuery();
        List<String> meatCategories = new ArrayList<>();
        while (rst.next()){
            meatCategories.add(
                    rst.getString(2)
            );
        }
        return meatCategories ;
    }


    public List<String> getSeaFoodCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Sea Food'").executeQuery();
        List<String> seaFoodCategories = new ArrayList<>();
        while (rst.next()){
            seaFoodCategories.add(
                    rst.getString(2)
            );
        }
        return seaFoodCategories ;
    }


    public List<String> getDessertCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Dessert'").executeQuery();
        List<String> dessertCategories = new ArrayList<>();
        while (rst.next()){
            dessertCategories.add(
                    rst.getString(2)
            );
        }
        return dessertCategories ;
    }


    public List<String> getPizzaCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Pizza'").executeQuery();
        List<String> pizzaCategories = new ArrayList<>();
        while (rst.next()){
            pizzaCategories.add(
                    rst.getString(2)
            );
        }
        return pizzaCategories ;
    }


    public List<String> getSandwichCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Sandwich'").executeQuery();
        List<String> sandwichCategories = new ArrayList<>();
        while (rst.next()){
            sandwichCategories.add(
                    rst.getString(2)
            );
        }
        return sandwichCategories ;
    }


    public List<String> getBurgerCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Burger'").executeQuery();
        List<String> burgerCategories = new ArrayList<>();
        while (rst.next()){
            burgerCategories.add(
                    rst.getString(2)
            );
        }
        return burgerCategories ;
    }


    public List<String> getSoftDrinkCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Soft Drink'").executeQuery();
        List<String> softDrinkCategories = new ArrayList<>();
        while (rst.next()){
            softDrinkCategories.add(
                    rst.getString(2)
            );
        }
        return softDrinkCategories;
    }


    public List<String> getHotDrinkCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Hot Drink'").executeQuery();
        List<String> hotDrinkCategories = new ArrayList<>();
        while (rst.next()){
            hotDrinkCategories.add(
                    rst.getString(2)
            );
        }
        return hotDrinkCategories ;
    }


    public List<String> getFreshJuiceCategories() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Meal WHERE categories='Fresh Juice'").executeQuery();
        List<String> freshJuiceCategories = new ArrayList<>();
        while (rst.next()){
            freshJuiceCategories.add(
                    rst.getString(2)
            );
        }
        return freshJuiceCategories ;
    }
}
