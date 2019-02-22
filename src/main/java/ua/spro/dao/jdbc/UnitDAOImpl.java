package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.UnitDAO;
import ua.spro.entity.unit.ConnectedUnit;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.util.Observable;
import java.util.Observer;

public class UnitDAOImpl implements UnitDAO, Observer {

    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();


    @Override
    public Integer save(ConnectedUnit unit) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO connected_units(unit_name , updated)" +
                            "VALUES (?, ?)" , Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, unit.getName());
            statement.setBoolean(2, unit.isUpdated());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys() ;
            if (generatedKeys.next()) {
                unit.setId((int) generatedKeys.getLong(1));
            }
            c.close();
            return unit.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(ConnectedUnit unit) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM connected_units WHERE unit_id = ?"
            );
            statement.setInt(1, unit.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean newInfoAdded(ConnectedUnit unit) {

        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE connected_units SET updated = false where unit_id <> ?;"
            );
            statement.setInt(1, unit.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public boolean unitIsUpdated(ConnectedUnit unit) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE connected_units SET updated = true where unit_id = ?;"
            );
            statement.setInt(1, unit.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public ObservableList<ConnectedUnit> getAll() {
        ObservableList<ConnectedUnit> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM connected_units  "

            );


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                list.add(new ConnectedUnit(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(ConnectedUnit n: list){
//            System.out.println(n);
//        }
        return list;
    }

    @Override
    public ConnectedUnit getById(Integer id) {
        ConnectedUnit unit = null;
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM connected_units where unit_id = ? "

            );

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                unit.setId(resultSet.getInt(1));
                unit.setName(resultSet.getString(2));
                unit.setUpdated(resultSet.getBoolean(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(ConnectedUnit n: list){
//            System.out.println(n);
//        }
        return unit;
    }

    @Override
    public boolean needUpdate(ConnectedUnit unit) {

        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT updated " +
                            "FROM connected_units where unit_id = ? "

            );

            statement.setInt(1, unit.getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return !resultSet.getBoolean(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
//        for(ConnectedUnit n: list){
//            System.out.println(n);
//        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ConnectionDBUtil){
            url = ConnectionDBUtil.getInstance().getUrl();
            login = ConnectionDBUtil.getInstance().getLogin();
            password = ConnectionDBUtil.getInstance().getPassword();
        }
    }
}
