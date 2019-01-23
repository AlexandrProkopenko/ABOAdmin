package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.TaskDAO;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.History;
import ua.spro.entity.task.Task;
import ua.spro.entity.task.TaskExt;
import ua.spro.entity.task.TaskSelectType;
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
    public boolean updateDone(TaskExt taskExt) {
        try (Connection c = DriverManager.getConnection(
                url, login, password)){

            PreparedStatement statement = c.prepareStatement(
                    "UPDATE tasks SET done = ? WHERE task_id = ?;"
            );
            statement.setBoolean(1, taskExt.isDone() ? false : true );
            statement.setInt(2, taskExt.getId());
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
    public ObservableList<TaskExt> getByHistories(ObservableList<History> histories, TaskSelectType taskSelectType) {

        ObservableList<TaskExt> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {

            for(History history: histories) {
                PreparedStatement statement = null;
                switch (taskSelectType){
                    case ALL:
                        statement = c.prepareStatement(
                                "select h.history_id , t.task_id, t.end_date, t.executor_id, t.done\n" +
                                        "from histories h \n" +
                                        "join histories_tasks ht\n" +
                                        "on h.history_id = ht.history_id \n" +
                                        "left join tasks t\n" +
                                        "on ht.task_id = t.task_id\n" +
                                        "having h.history_id = ?; "

                        );
                        statement.setInt(1, history.getId());
                        break;

                    case DONE:
                        statement = c.prepareStatement(
                                "select h.history_id , t.task_id, t.end_date, t.executor_id, t.done\n" +
                                        "from histories h \n" +
                                        "join histories_tasks ht\n" +
                                        "on h.history_id = ht.history_id \n" +
                                        "left join tasks t\n" +
                                        "on ht.task_id = t.task_id\n" +
                                        "having h.history_id = ? and t.done = true; "

                        );
                        statement.setInt(1, history.getId());
                        break;
                    case UNDONE:
                        statement = c.prepareStatement(
                                "select h.history_id , t.task_id, t.end_date, t.executor_id, t.done\n" +
                                        "from histories h \n" +
                                        "join histories_tasks ht\n" +
                                        "on h.history_id = ht.history_id \n" +
                                        "left join tasks t\n" +
                                        "on ht.task_id = t.task_id\n" +
                                        "having h.history_id = ? and t.done = false; "

                        );
                        statement.setInt(1, history.getId());
                        break;

                        default:
                            break;
                }



                if(statement == null) return null;
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {

                    list.add(new TaskExt(
                            resultSet.getInt(2),
                            (resultSet.getTimestamp(3)).toLocalDateTime(),
                            resultSet.getInt(4),
                            resultSet.getBoolean(5),
                            formatComment(history.getComment()),
                            history.getUserId()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("TasksExtList in Task DAO:");
        for(TaskExt t: list){

            System.out.println(t);
        }
        return list;

    }

    private String formatComment(String comment){
        if(comment == null) return null;
        int start = comment.indexOf('.') + 2;
        int end = comment.lastIndexOf("для");
//        System.out.println("start " + start + " end " + end);
        if(start != -1 && end != -1) {
            comment = comment.substring(start, end);
        }
        return comment;
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
