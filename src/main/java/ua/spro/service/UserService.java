package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.User;

public interface UserService {


    boolean save(User user);

    User getById(Integer id);

    boolean update(User user);

    boolean delete(User user);

    ObservableList<User> getAll();
}
