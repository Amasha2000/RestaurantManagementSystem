package controller;

import db.DbConnection;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageEmployeeController implements ManageEmployee {

    @Override
    public boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                    "INSERT INTO Employee(empId,empName,empAddress,empNic,empPhoneNumber,empDescription) VALUES(?,?,?,?,?,?)");
            stm.setObject(1, e.getEmpId());
            stm.setObject(2, e.getEmpName());
            stm.setObject(3, e.getEmpAddress());
            stm.setObject(4, e.getEmpNic());
            stm.setObject(5, e.getEmpPhoneNumber());
            stm.setObject(6, e.getEmpDescription());
            return stm.executeUpdate() > 0;
        }catch(Exception exception){
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("UPDATE Employee SET empName=?,empAddress=?,empPhoneNumber=?,empDescription=? WHERE empId=?");
        stm.setObject(1,e.getEmpName());
        stm.setObject(2,e.getEmpAddress());
        stm.setObject(3,e.getEmpPhoneNumber());
        stm.setObject(4,e.getEmpDescription());
        stm.setObject(5,e.getEmpId());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT empId,empName,empAddress,empNic,empPhoneNumber,empDescription FROM Employee");
        ResultSet rst=stm.executeQuery();
        ArrayList<Employee> employee=new ArrayList<>();
        while (rst.next()){
            employee.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
                    ));
        }
       return employee;
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee WHERE empId='"+employeeId+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Employee> getEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm=DbConnection.getInstance().getConnection().prepareStatement("SELECT empId,empName,empAddress,empNic,empPhoneNumber,empDescription FROM Employee WHERE empId=?");
        stm.setObject(1,employeeId);
        ResultSet rst=stm.executeQuery();
        ArrayList<Employee> searchEmployee= new ArrayList<>();
        if(rst.next()){
            searchEmployee.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
            return searchEmployee;
        }else{
            return null;
        }
    }
}
