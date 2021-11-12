package controller;

import db.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ManageLogInController {

    public boolean getUserDetails(String userName,String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM User WHERE userName='"+userName+"' AND password='"+password+"'").executeQuery();
        if(rst.next()){
            return true;
        }
         return false;
    }

    public boolean getAdminDetails(String name,String password) throws SQLException, ClassNotFoundException {
        ResultSet rst=DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Owner WHERE name='"+name+"' AND password='"+password+"'").executeQuery();
        if(rst.next()){
            return  true;
        }
        return false;
    }
}
