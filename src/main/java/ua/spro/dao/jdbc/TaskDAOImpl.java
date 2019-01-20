package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.TaskDAO;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.History;
import ua.spro.entity.task.Task;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.util.Observable;
import java.util.Observer;

public class TaskDAOImpl implements TaskDAO, Observer {

    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();

    @Override
    public Integer save(Task task) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO tasks(end_date , executor_id, done)" +
                            "VALUES (?, ?, ?)" , Statement.RETURN_GENERATED_KEYS
            );
            Timestamp endDate = Timestamp.valueOf(task.getEndDate());
            statement.setTimestamp(1, endDate );
            statement.setInt(2, task.getExecutorId());
            statement.setBoolean(3, task.isDone());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys() ;
            if (generatedKeys.next()) {
                task.setId((int) generatedKeys.getLong(1));
            }
            c.close();
            return task.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Task getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Task task) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE tasks SET end_date = ?, executor_id = ?, done = ? WHERE task_id = ?;"
            );
            Timestamp endDate = Timestamp.valueOf(task.getEndDate());
            statement.setTimestamp(1, endDate );
            statement.setInt(2, task.getExecutorId());
            statement.setBoolean(3, task.isDone());
            statement.setInt(4, task.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public boolean updateDone(Task task) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE tasks SET done = ? WHERE task_id = ?;"
            );
            statement.setBoolean(1, task.isDone() ? false : true );
            statement.setInt(2, task.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("update success");
        return true;
    }

    @Override
    public boolean delete(Task task) {
        return false;
    }

    @Override
    public ObservableList<Task> getAll() {
        ObservableList<Task> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * " +
                            "FROM tasks  "

            );


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                list.add(new Task(
                        resultSet.getInt(1),
                        (resultSet.getTimestamp(2)).toLocalDateTime(),
                        resultSet.getInt(3),
                        resultSet.getBoolean(4)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public boolean saveLink(History history, Task task) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO histories_tasks( history_id, task_id) " +
                            "VALUES (?, ?)"
            );

            statement.setInt(1,history.getId() );
            statement.setInt(2, task.getId());
            statement.execute();

            /* */
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ObservableList<Task> getByClient(Client client) {
        /*
        ObservableList<History> list = FXCollections.observableArrayList();
        Integer id = client.getId();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "select cl.client_id, h.date_h, h.note, h.user_id\n" +
                            "from clients cl\n" +
                            "join clients_history ch\n" +
                            "on cl.client_id = ch.client_id\n" +
                            "left join histories h\n" +
                            "on ch.history_id = h.history_id\n" +
                            "having cl.client_id = ?  "

            );
            statement.setInt(1, id);


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                list.add(new History(
                        resultSet.getInt(1),
                        (resultSet.getTimestamp(2)).toLocalDateTime(),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(History n: list){
            System.out.println(n);
        }
        return list;
        */
        return null;
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
