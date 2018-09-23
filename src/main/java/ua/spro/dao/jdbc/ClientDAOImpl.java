package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.ClientDAO;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.entity.Status;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;

public class ClientDAOImpl implements ClientDAO {

    private static String url = ConnectionDBUtil.getUrl();
    private static String login = ConnectionDBUtil.getLogin();
    private static String password = ConnectionDBUtil.getPassword();

    private HistoryDAOImpl historyDAO;

    public ClientDAOImpl(){
        historyDAO = new HistoryDAOImpl();

    }

    public boolean testConnectionToDB(){
        try (Connection c = DriverManager.getConnection(url, login, password)){
            System.out.println(url + login + password);
            System.out.println("Connected to DB");
            return true;
        } catch (SQLException e) {
            System.out.println("Can`t connect to DB");
            e.printStackTrace();
            return false;
        }
    }



    public boolean clearTable(){
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "DELETE from clients_history;"
            );
            statement.execute();
            statement = c.prepareStatement(
                     "DELETE from clients;"
            );
            statement.execute();
            statement = c.prepareStatement(
                    "ALTER TABLE clients auto_increment = 1;"
            );
            statement.execute();

            statement = c.prepareStatement(
                      "DELETE  from histories"
            );
            statement.execute();
            statement = c.prepareStatement(
                    "ALTER TABLE histories auto_increment = 1;"
            );
            statement.execute();
            statement = c.prepareStatement(
                    "DELETE  from departments"
            );
            statement.execute();
            statement = c.prepareStatement(
                    "ALTER TABLE departments auto_increment = 1;"
            );
            statement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveClientAndHistory(Client client, History history){
        client.setId(save(client));
        System.out.println(client);
        history.setId(historyDAO.save(history));
        System.out.println(history);

        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO abo.clients_history(client_id, history_id)" +
                            "VALUES (?, ?)"
            );
            statement.setInt(1, client.getId());
            statement.setInt(2, history.getId());
//            statement.RETURN_GENERATED_KEYS;
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Integer save(Client client) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){
            System.out.println("departmentId " + client.getDepartmentId());
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO abo.clients(child_name, birthday, parent_name, phone, location, department_id, status_id)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)" , Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, client.getChildName());
            statement.setDate(2, java.sql.Date.valueOf(client.getBirthday()));
            statement.setString(3, client.getParentName());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getLocation());
            statement.setInt(6, client.getDepartmentId());
            statement.setInt(7, client.getStatusId());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys() ;
                if (generatedKeys.next()) {
                    client.setId((int) generatedKeys.getLong(1));
                }
            System.out.println(" ClientId    " +client.getId());

            return client.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Client client) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE abo.clients SET child_name = ?, birthday = ?, parent_name = ?, phone = ?, location = ?, department_id = ?, status_id = ? WHERE client_id = ?;"
            );
            statement.setString(1, client.getChildName());
            statement.setDate(2, java.sql.Date.valueOf(client.getBirthday()));
            statement.setString(3, client.getParentName());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getLocation());
            statement.setInt(6, client.getDepartmentId());
            statement.setInt(7, client.getStatusId());
            statement.setInt(8, client.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public boolean delete(Client client) {
        return false;
    }

    @Override
    public ObservableList<Client> getAll() {
        ObservableList<Client> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {


            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM abo.clients  "

            );


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        (resultSet.getDate(3)).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("inDao");
//        for(Client n: list){
//            System.out.println(n);
//            System.out.println("ddd");
//        }
        return list;
    }

    public ObservableList<Client> getClientsByStatus(Status status){
        if(status.getClientStatus().equals("Всі")){
            return getAll();
        }

        ObservableList<Client> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM abo.clients  " +
                            "WHERE status_id = ?"

            );
            statement.setInt(1, status.getStatusId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        (resultSet.getDate(3)).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("inDao");
        for(Client n: list){
            System.out.println(n);
            System.out.println("ddd");
        }
        return list;
    }

    public boolean setStatusToClient(Client client, Status newStatus){
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE abo.clients SET status_id = ?" +
                            " WHERE client_id = ?"
            );


            statement.setInt(1, newStatus.getStatusId());
            statement.setInt(2, client.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ObservableList<Client> getClientsByDepartment(Department department){
        if(department.getClientDepartment().equals("Всі")){
            return getAll();
        }

        ObservableList<Client> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM abo.clients  " +
                            "WHERE department_id = ?"

            );
            statement.setInt(1, department.getDepartmentId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        (resultSet.getDate(3)).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("inDao");
        for(Client n: list){
            System.out.println(n);
            System.out.println("ddd");
        }
        return list;
    }

    public boolean setDepartmentToClient(Client client, Department newDepartment){
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE abo.clients SET department_id = ?" +
                            " WHERE client_id = ?"
            );


            statement.setInt(1, newDepartment.getDepartmentId());
            statement.setInt(2, client.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department){
        if(department.getClientDepartment().equals("Всі")){
            return getClientsByStatus(status);
        }
        if(status.getClientStatus().equals("Всі")){
            return getClientsByDepartment(department);
        }

        ObservableList<Client> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM abo.clients WHERE department_id = ? AND status_id = ? "

            );
            statement.setInt(1, department.getDepartmentId());
            statement.setInt(2, status.getStatusId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        (resultSet.getDate(3)).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("inDao");
        for(Client n: list){
            System.out.println(n);
            System.out.println("ddd");
        }
        return list;
    }

}
