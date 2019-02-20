package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.ClientDAO;
import ua.spro.entity.User;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.entity.task.TaskSelectType;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class ClientDAOImpl implements ClientDAO, Observer {

    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();

    private HistoryDAOImpl historyDAO;


    public ClientDAOImpl(){

        ConnectionDBUtil.getInstance().addObserver(this);
        historyDAO = new HistoryDAOImpl();


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
            c.close();
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
                    "INSERT INTO clients_history(client_id, history_id)" +
                            "VALUES (?, ?)"
            );
            statement.setInt(1, client.getId());
            statement.setInt(2, history.getId());
//            statement.RETURN_GENERATED_KEYS;
            statement.execute();
            c.close();
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
                    "INSERT INTO clients(child_name, birthday, parent_name, phone, location, department_id, status_id)" +
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
            c.close();
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
                            "FROM clients  "

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
        System.err.println("Client DAO. Status: " + status + "    department " + department);
        ObservableList<Client> list = FXCollections.observableArrayList();
        if(department == null || status == null)
            return list;


        if(department.getClientDepartment().equals("Всі")){
            return getClientsByStatus(status);
        }

        if(status.getClientStatus().equals("Всі")){
            return getClientsByDepartment(department);
        }

//        ObservableList<Client> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM clients WHERE department_id = ? AND status_id = ? "

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
        }
        return list;
    }

    @Override
    public ObservableList<Client> getClientsByFilters(Status status, Department department,
                                                      LocalDate dateFrom, LocalDate dateTo, TaskSelectType taskSelectType, User author, User executor) {

        ObservableList<Client> list = FXCollections.observableArrayList();

        if(department == null || status == null)
            return list;

        try(Connection c = DriverManager.getConnection(url, login, password)) {
            StringBuilder query = new StringBuilder("select\n" +
                    "cl.client_id, cl.child_name, cl.birthday, cl.parent_name, cl.phone, cl.location, cl.department_id, cl.status_id,\n" +
                    "t.end_date, t.done, h.user_id, t.executor_id\n" +
                    "\n" +
                    "from clients cl\n" +
                    "join clients_history ch\n" +
                    "on cl.client_id = ch.client_id\n" +
                    "left join histories h\n" +
                    "on ch.history_id = h.history_id\n" +
                    "join histories_tasks ht\n" +
                    "on h.history_id = ht.history_id \n" +
                    "left join tasks t\n" +
                    "on ht.task_id = t.task_id\n" +
                    "\n" +
                    "having "
            );

            if(department.getClientDepartment().equals("Всі")){
            }else{
                query.append(" cl.department_id = ").
                        append(department.getDepartmentId()).
                        append(" and ");
            }

            if(status.getClientStatus().equals("Всі")){
            }else{
                query.append("cl.status_id = ").
                        append(status.getStatusId()).
                        append(" and ");
            }

            query.append("t.end_date >= '").
                    append(dateFrom).
                    append("'");

            query.append(" and t.end_date <= '").
                    append(dateTo).
                    append("'");

            switch (taskSelectType){
                case DONE:
                    query.append(" and t.done = true ");
                    break;
                case UNDONE:
                    query.append(" and t.done = false ");
                    break;
                case ALL:
                    break;
            }

            if(author.getLogin().equals("Всі")){
            }else {
                query.append("and h.user_id = ").
                        append(author.getUserId());
            }

            if(executor.getLogin().equals("Всі")){
            }else {
                query.append(" and t.executor_id = ").
                        append(executor.getUserId());
            }
            query.append(";");

            System.out.println(query.toString());

            PreparedStatement statement = c.prepareStatement(query.toString());
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
}
