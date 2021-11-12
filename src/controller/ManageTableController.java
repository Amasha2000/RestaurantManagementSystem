package controller;

import db.DbConnection;
import model.Table;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageTableController implements ManageTable{

    @Override
    public boolean saveTable(Table t) throws SQLException, ClassNotFoundException {
       try {
           PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Tabless(table_number,table_code,numberOfSeats) VALUES(?,?,?)");
            stm.setObject(1,t.getTable_number());
            stm.setObject(2,t.getTable_code());
            stm.setObject(3,t.getNumberOfSeats());
            return stm.executeUpdate()>0;
       }catch (Exception e){
           return false;
       }
    }

    @Override
    public boolean updateTable(Table t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Tabless SET numberOfSeats=? WHERE table_number=?");
        stm.setObject(1,t.getNumberOfSeats());
        stm.setObject(2,t.getTable_number());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Table> getAllTables() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT table_number,table_code,numberOfSeats FROM Tabless");
        ResultSet rst=stm.executeQuery();
        ArrayList<Table> tables=new ArrayList<>();
        while (rst.next()){
            tables.add(new Table(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getInt(3)
            ));
        }
        return tables;
    }

    @Override
    public boolean deleteTable(int tableNumber) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Tabless WHERE table_number='"+tableNumber+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Table> getTable(int tableNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT table_number,table_code,numberOfSeats FROM Tabless WHERE table_number=?");
        stm.setObject(1,tableNumber);
        ResultSet rst=stm.executeQuery();
        ArrayList<Table> searchTable= new ArrayList<>();
        if(rst.next()){
            searchTable.add(new Table(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getInt(3)
            ));
            return searchTable;
        }else{
        return null;
        }
    }



    public List<String> getTableCodes() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Tabless").executeQuery();
        List<String> codes = new ArrayList<>();
        while (rst.next()){
            codes.add(
                    rst.getString(3)
            );
        }
        return codes;
    }
}
