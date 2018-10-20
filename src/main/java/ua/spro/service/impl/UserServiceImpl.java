package ua.spro.service.impl;

import javafx.collections.ObservableList;
import ua.spro.dao.UserDAO;
import ua.spro.dao.jdbc.UserDAOImpl;
import ua.spro.entity.User;
import ua.spro.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO dao;

    public UserServiceImpl() {
        dao = new UserDAOImpl();
    }

    @Override
    public boolean save(User user) {
        return dao.save(user);
    }

    @Override
    public User getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public boolean update(User user) {
        return dao.update(user);
    }

    @Override
    public boolean delete(User user) {
        return dao.delete(user);
    }

    @Override
    public ObservableList<User> getAll() {
        return dao.getAll();
    }
}
