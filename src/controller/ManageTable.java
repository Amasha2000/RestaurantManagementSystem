package controller;

import model.Table;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageTable {
    boolean saveTable(Table t) throws SQLException, ClassNotFoundException;
    boolean updateTable(Table t) throws SQLException, ClassNotFoundException;
    ArrayList<Table> getAllTables() throws SQLException, ClassNotFoundException;
    boolean deleteTable(int tableNumber) throws SQLException, ClassNotFoundException;
    ArrayList<Table> getTable(int tableNumber) throws SQLException, ClassNotFoundException;


}
