package controller;

import model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageEmployee {
    boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException;
    ArrayList<Employee> getAllEmployees() throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException;
    ArrayList<Employee> getEmployee(String employeeId) throws SQLException, ClassNotFoundException;
}
