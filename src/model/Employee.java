package model;

public class Employee {
            private String empId;
            private String empName;
            private String empAddress;
            private String empNic;
            private String empPhoneNumber;
            private String empDescription;

    public Employee() {
    }

    public Employee(String empId, String empName, String empAddress, String empNic, String empPhoneNumber, String empDescription) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setEmpAddress(empAddress);
        this.setEmpNic(empNic);
        this.setEmpPhoneNumber(empPhoneNumber);
        this.setEmpDescription(empDescription);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpNic() {
        return empNic;
    }

    public void setEmpNic(String empNic) {
        this.empNic = empNic;
    }

    public String getEmpPhoneNumber() {
        return empPhoneNumber;
    }

    public void setEmpPhoneNumber(String empPhoneNumber) {
        this.empPhoneNumber = empPhoneNumber;
    }

    public String getEmpDescription() {
        return empDescription;
    }

    public void setEmpDescription(String empDescription) {
        this.empDescription = empDescription;
    }
}
