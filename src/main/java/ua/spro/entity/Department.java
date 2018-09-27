package ua.spro.entity;

public class Department {

    private Integer departmentId;
    private String clientDepartment;

    public Department(Integer departmentId, String clientDepartment) {
        this.departmentId = departmentId;
        this.clientDepartment = clientDepartment;
    }

    public Department(String clientDepartment) {
        this.clientDepartment = clientDepartment;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getClientDepartment() {
        return clientDepartment;
    }

    public void setClientDepartment(String clientDepartment) {
        this.clientDepartment = clientDepartment;
    }

    @Override
    public String toString() {
        return clientDepartment;
    }
}
