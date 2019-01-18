package ua.spro.dao.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.ActionStatisticDAO;
import ua.spro.entity.statistic.ActionStatistic;
import ua.spro.util.ConnectionDBUtil;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class ActionStatisticDAOImpl implements ActionStatisticDAO, Observer {
    private static String url = ConnectionDBUtil.getInstance().getUrl();
    private static String login = ConnectionDBUtil.getInstance().getLogin();
    private static String password = ConnectionDBUtil.getInstance().getPassword();

    public ActionStatisticDAOImpl() {
        ConnectionDBUtil.getInstance().addObserver(this);
    }

    @Override
    public ObservableList<ActionStatistic> getDataByUserId(Integer userId) {
        ObservableList<ActionStatistic> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "select cl.child_name, cl.birthday, d.cl_department, s.cl_status, h.date_h, h.note, u.login, u.user_id\n" +
                            "from clients cl\n" +
                            "join departments d\n" +
                            "on cl.department_id = d.department_id\n" +
                            "join statuses s\n" +
                            "on cl.status_id = s.status_id\n" +
                            "join clients_history ch\n" +
                            "on cl.client_id = ch.client_id\n" +
                            "left join histories h\n" +
                            "on ch.history_id = h.history_id\n" +
                            "left join users u\n" +
                            "on h.user_id = u.user_id\n" +
                            "having  u.user_id = ?;"

            );
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
/*              збираємо інфо про клієнта:
                ім'я
                перетворюємо дату народження у вік
                філія
                статус

                список результатів:
                1-4 інфо про клієнта
                5 - дата створення запису
                6 - коментар
                7 - логін автора
*/
                StringBuilder sb = new StringBuilder();
                    sb.
                            append(resultSet.getString(1)).
                            append(" ").
                            append( calculateAgeByBirthday( resultSet.getDate(2).toLocalDate() ) ).
                            append(" ").
                            append(resultSet.getString(3)).
                            append(" ").
                            append(resultSet.getString(4));
                list.add(new ActionStatistic(
                        resultSet.getString(7),
                        (resultSet.getTimestamp(5)).toLocalDateTime(),
                        sb.toString(),
                        resultSet.getString(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ObservableList<ActionStatistic> getDataByUserIdAndDate(Integer userId, LocalDateTime from, LocalDateTime to) {
        ObservableList<ActionStatistic> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "select cl.child_name, cl.birthday, d.cl_department, s.cl_status, h.date_h, h.note, u.login, u.user_id\n" +
                            "from clients cl\n" +
                            "join departments d\n" +
                            "on cl.department_id = d.department_id\n" +
                            "join statuses s\n" +
                            "on cl.status_id = s.status_id\n" +
                            "join clients_history ch\n" +
                            "on cl.client_id = ch.client_id\n" +
                            "left join histories h\n" +
                            "on ch.history_id = h.history_id\n" +
                            "left join users u\n" +
                            "on h.user_id = u.user_id\n" +
                            "having  u.user_id = ?\n" +
                            "and h.date_h between ? and ?;"

            );
            statement.setInt(1, userId);
            statement.setTimestamp(2, Timestamp.valueOf(from));
            statement.setTimestamp(3, Timestamp.valueOf(to));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
/*              збираємо інфо про клієнта:
                ім'я
                перетворюємо дату народження у вік
                філія
                статус

                список результатів:
                1-4 інфо про клієнта
                5 - дата створення запису
                6 - коментар
                7 - логін автора
*/
                StringBuilder sb = new StringBuilder();
                sb.
                        append(resultSet.getString(1)).
                        append(" ").
                        append( calculateAgeByBirthday( resultSet.getDate(2).toLocalDate() ) ).
                        append(" ").
                        append(resultSet.getString(3)).
                        append(" ").
                        append(resultSet.getString(4));
                list.add(new ActionStatistic(
                        resultSet.getString(7),
                        (resultSet.getTimestamp(5)).toLocalDateTime(),
                        sb.toString(),
                        resultSet.getString(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ObservableList<ActionStatistic> getDataByDate(LocalDateTime from, LocalDateTime to) {
        ObservableList<ActionStatistic> list = FXCollections.observableArrayList();
        try(Connection c = DriverManager.getConnection(url, login, password)) {
            PreparedStatement statement = c.prepareStatement(
                    "select cl.child_name, cl.birthday, d.cl_department, s.cl_status, h.date_h, h.note, u.login, u.user_id\n" +
                            "from clients cl\n" +
                            "join departments d\n" +
                            "on cl.department_id = d.department_id\n" +
                            "join statuses s\n" +
                            "on cl.status_id = s.status_id\n" +
                            "join clients_history ch\n" +
                            "on cl.client_id = ch.client_id\n" +
                            "left join histories h\n" +
                            "on ch.history_id = h.history_id\n" +
                            "left join users u\n" +
                            "on h.user_id = u.user_id\n" +
                            "having h.date_h between ? and ?;"

            );
            statement.setTimestamp(1, Timestamp.valueOf(from));
            statement.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
/*              збираємо інфо про клієнта:
                ім'я
                перетворюємо дату народження у вік
                філія
                статус

                список результатів:
                1-4 інфо про клієнта
                5 - дата створення запису
                6 - коментар
                7 - логін автора
*/
                StringBuilder sb = new StringBuilder();
                sb.
                        append(resultSet.getString(1)).
                        append(" ").
                        append( calculateAgeByBirthday( resultSet.getDate(2).toLocalDate() ) ).
                        append(" ").
                        append(resultSet.getString(3)).
                        append(" ").
                        append(resultSet.getString(4));
                list.add(new ActionStatistic(
                        resultSet.getString(7),
                        (resultSet.getTimestamp(5)).toLocalDateTime(),
                        sb.toString(),
                        resultSet.getString(6)
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

    private Double calculateAgeByBirthday(LocalDate birthday){
        int year = birthday.getYear();
        int month = birthday.getMonthValue();
        LocalDate now = LocalDate.now();
        double result =  (double) ((now.getYear()- year ) + (double)(now.getMonthValue()-month)*1/12);
        DecimalFormat newFormat = new DecimalFormat("##.#");
//        String age = newFormat.format(result);
//        System.out.println(result);
//        System.out.println(newFormat.format(result));

        char c;
        String age = newFormat.format(result);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <age.length(); i++) {
            c = age.charAt(i);
            if(c == ',') c = '.';
            sb.append(c);
        }
        age = sb.toString();
        try{
            result = Double.parseDouble(age);
        }catch (Exception e){
            System.out.println("Неможливо перетворити вік в число!");
            e.printStackTrace();
        }

        return result;
    }
}
