package controller;

import model.Meal;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageMeal {
    boolean saveMeal(Meal m) throws SQLException, ClassNotFoundException;
    boolean updateMeal(Meal m) throws SQLException, ClassNotFoundException;
    ArrayList<Meal> getAllMeals() throws SQLException, ClassNotFoundException;
    boolean deleteMeal(String subCategory) throws SQLException, ClassNotFoundException;
    ArrayList<Meal> getMeal(String subCategory) throws SQLException, ClassNotFoundException;
}
