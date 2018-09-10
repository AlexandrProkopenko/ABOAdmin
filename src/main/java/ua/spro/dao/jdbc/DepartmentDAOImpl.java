package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.DepartmentDAO;
import ua.spro.entity.Department;

import java.sql.*;

public class DepartmentDAOImpl implements DepartmentDAO {

    private static String url = "jdbc:mysql://localhost:3306/abo ?serverTimezone=UTC&useSSL=false";
    private static String login = "root";
    private static String password = "1111";

    @Override
    public Integer save(Department department) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO abo.departments(cl_department)" +
                            "VALUES (?)" , Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, department.getClientDepartment());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys() ;
            if (generatedKeys.next()) {
                department.setDepartmentId((int) generatedKeys.getLong(1));
            }


            return department.getDepartmentId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        ObservableList<Department> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM abo.departments  "

            );


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Department(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("inDao department");
//        for(Department n: list){
//            System.out.println(n);
//            System.out.println("ddd");
//        }
        return list;
    }

    public Integer getIdByClientDepartment(String clientDepartment){
        Integer result = null;
        ObservableList<Department> list = FXCollections.observableArrayList();
//        System.out.println("in method");
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM abo.departments WHERE cl_department = ?  "

            );
            statement.setString(1,  clientDepartment );

            ResultSet resultSet = statement.executeQuery();
//            System.out.println("Query executed");
            while (resultSet.next()) {

                list.add(new Department(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }
            if(!list.isEmpty())
            result = list.get(0).getDepartmentId();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("inDao department");
//        for(Department n: list){
//            System.out.println(n);
//            System.out.println("ddd");
//        }


        return result;
    }
}
