package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.User;

public interface UserDAO {


    boolean save(User user);

    User getById(Integer id);

    boolean update(User user);

    boolean delete(User user);

    ObservableList<User> getAll();
}
