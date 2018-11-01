package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.User;

public interface UserDAO {


    Integer save(User user);

    User getById(Integer id);

    boolean update(User user);

    boolean delete(User user);

    ObservableList<User> getAll();

    User getUserWithId(User user);
}
