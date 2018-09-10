package ua.spro.dao.jdbc;

import com.sun.org.apache.xerces.internal.impl.XMLStreamReaderImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.dao.StatusDAO;
import ua.spro.entity.Status;

import java.sql.*;

public class StatusDAOImpl implements StatusDAO {

    private static String url = "jdbc:mysql://localhost:3306/abo ?serverTimezone=UTC&useSSL=false";
    private static String login = "root";
    private static String password = "1111";

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
                            "FROM abo.statuses  "

            );


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Status(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("inDao status");
//        for(Status n: list){
//            System.out.println(n);
//        }
        return list;
    }

    public Integer getIdByClientStatus(String clientStatus){
        Integer result = null;
        ObservableList<Status> list = FXCollections.observableArrayList();
//        System.out.println("in method");
//        try {
//            Class.forName("com.mysql.jdbc.driver.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        try(Connection c = DriverManager.getConnection(url, login, password) ) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM abo.statuses WHERE cl_status = ?  " , Statement.RETURN_GENERATED_KEYS

            );
            statement.setString(1,  clientStatus );

            ResultSet resultSet = statement.executeQuery();
//            System.out.println("Query executed");
            while (resultSet.next()) {

                list.add(new Status(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                ));
            }
        result = list.get(0).getStatusId();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("inDao");
//        for(Status n: list){
//            System.out.println(n);
//            System.out.println("ddd");
//        }


        return result;
    }


}
