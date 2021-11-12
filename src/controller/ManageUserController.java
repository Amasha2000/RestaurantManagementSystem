package controller;

import db.DbConnection;
import model.Owner;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageUserController implements ManageUser{

    @Override
    public boolean saveUser(User u) throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO User(userId,userName,password) VALUES(?,?,?)");
            stm.setObject(1, u.getUserId());
            stm.setObject(2, u.getUserName());
            stm.setObject(3, u.getPassword());
            return stm.executeUpdate() > 0;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUser(User u) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE User SET userName=?,password=? WHERE userId=?");
        stm.setObject(1,u.getUserName());
        stm.setObject(2,u.getPassword());
        stm.setObject(3,u.getUserId());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT userId,userName,password FROM User");
        ResultSet rst=stm.executeQuery();
        ArrayList<User> user=new ArrayList<>();
        while (rst.next()){
            user.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return user;
    }

    @Override
    public boolean deleteUser(String userIds) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM User WHERE userId='"+userIds+"'").executeUpdate()>0){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<User> getUser(String userIds) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT userId,userName,password FROM User WHERE userId=?");
        stm.setObject(1,userIds);
        ResultSet rst=stm.executeQuery();
        ArrayList<User> searchUser=new ArrayList<>();
        if(rst.next()){
            searchUser.add(new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
            return searchUser;
        }else{
            return null;
        }
    }


    public String getAdminUserName() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Owner WHERE ownerId='ow-01'").executeQuery();
        String userName=null;
        while (rst.next()){
            userName=rst.getString(2);

        }
        return userName ;
    }


    public String getAdminPassword() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Owner WHERE ownerId='ow-01'").executeQuery();
        String password=null;
        while (rst.next()){
            password=rst.getString(3);
        }
        return password ;
    }

    public boolean updateAdmin(Owner a) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Owner SET name=?,password=? WHERE ownerId='ow-01'");
        stm.setObject(1,a.getName());
        stm.setObject(2,a.getPassword());
        return stm.executeUpdate()>0;
    }
}
