package controller;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageUser {
    boolean saveUser(User u) throws SQLException, ClassNotFoundException;
    boolean updateUser(User u) throws SQLException, ClassNotFoundException;
    ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException;
    boolean deleteUser (String userIds) throws SQLException, ClassNotFoundException;
    ArrayList<User> getUser(String userIds) throws SQLException, ClassNotFoundException;
}
