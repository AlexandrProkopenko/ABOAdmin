package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.client.Department;

public interface DepartmentDAO {

    Integer save(Department department);

    Department getById(Integer id);

    boolean update(Department department);

    boolean delete(Department department);

    ObservableList<Department> getAll();
}
