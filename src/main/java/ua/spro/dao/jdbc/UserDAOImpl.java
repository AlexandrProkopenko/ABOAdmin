package ua.spro.dao.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.UserDAO;
import ua.spro.entity.User;
import ua.spro.util.ConnectionDBUtil;

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
    public boolean save(User user) {
        return false;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public ObservableList<User> getAll() {
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
