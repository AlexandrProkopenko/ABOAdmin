package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.UserDAO;
import ua.spro.entity.User;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.util.Observable;
import java.util.Observer;

public class UserDAOImpl implements UserDAO, Observer {

    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();

    public UserDAOImpl() {
        ConnectionDBUtil.getInstance().addObserver(this);
    }

    @Override
    public Integer save(User user) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO abo.users(login, user_password)" +
                            "VALUES (?, ?)" , Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys() ;
            if (generatedKeys.next()) {
                user.setUserId((int) generatedKeys.getLong(1));
            }
            System.out.println(" UserId    " + user.getUserId());

            return user.getUserId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getById(Integer id) {
        User user = new User();
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "select * from abo.users WHERE user_id = ?"
            );

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user.setUserId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE abo.users SET login = ?, user_password = ? WHERE user_id = ?;"
            );
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getUserId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public boolean delete(User user) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM abo.users WHERE user_id = ?"
            );
            statement.setInt(1, user.getUserId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ObservableList<User> getAll() {
        ObservableList<User> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM abo.users"

            );

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ConnectionDBUtil){
            url = ConnectionDBUtil.getInstance().getUrl();
            login = ConnectionDBUtil.getInstance().getLogin();
            password = ConnectionDBUtil.getInstance().getPassword();
        }
    }

    @Override
    public User getUserWithId(User user) {

//        System.out.println("DAO. \nid = "+user.getUserId() + "\nlogin = "+user.getLogin()+"\npassword =" + user.getPassword());

        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "select * from abo.users WHERE login = ? AND user_password = ?;"
            );

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet);
                user.setUserId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
//                System.out.println("DAO. \nid = "+user.getUserId() + "\nlogin = "+user.getLogin()+"\npassword =" + user.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("DAO. \nid = "+user.getUserId() + "\nlogin = "+user.getLogin()+"\npassword =" + user.getPassword());
        return user;
    }
}
