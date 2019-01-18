package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.StatusDAO;
import ua.spro.entity.client.Status;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.util.Observable;
import java.util.Observer;

public class StatusDAOImpl implements StatusDAO, Observer {

    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();

    public StatusDAOImpl() {
        ConnectionDBUtil.getInstance().addObserver(this);
    }

    @Override
    public boolean save(Status clientStatus) {
        return false;
    }

    @Override
    public Status getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Status clientStatus) {
        return false;
    }

    @Override
    public boolean delete(Status clientStatus) {
        return false;
    }

    @Override
    public ObservableList<Status> getAll() {

        ObservableList<Status> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM statuses  "

            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Status(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }
        c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer getIdByClientStatus(String clientStatus){
        Integer result = null;
        ObservableList<Status> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password) ) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM abo.statuses WHERE cl_status = ?  " , Statement.RETURN_GENERATED_KEYS

            );
            statement.setString(1,  clientStatus );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                list.add(new Status(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }
            if(!list.isEmpty())
        result = list.get(0).getStatusId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
