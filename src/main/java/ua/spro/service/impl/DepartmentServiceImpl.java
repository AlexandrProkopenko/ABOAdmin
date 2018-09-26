package ua.spro.service.impl;

import javafx.collections.ObservableList;
import ua.spro.dao.jdbc.DepartmentDAOImpl;
import ua.spro.entity.Department;
import ua.spro.service.DepartmentService;

import java.util.Observable;

public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentDAOImpl dao;

    public DepartmentServiceImpl(Observable observable) {
        dao = new DepartmentDAOImpl(observable);
    }

    @Override
    public Integer save(Department department) {
        return dao.save(department);
    }

    @Override
    public Department getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Department department) {
        return false;
    }

    @Override
    public boolean delete(Department department) {
        return false;
    }

    @Override
    public ObservableList<Department> getAll() {
        return dao.getAll();
    }

    public Integer getIdByClientDepartment(String clientDepartment){
        return dao.getIdByClientDepartment(clientDepartment);
    }
}
