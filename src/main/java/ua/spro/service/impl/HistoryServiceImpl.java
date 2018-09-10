package ua.spro.service.impl;

import javafx.collections.ObservableList;
import ua.spro.dao.jdbc.HistoryDAOImpl;
import ua.spro.entity.Client;
import ua.spro.entity.History;
import ua.spro.service.HistoryService;

public class HistoryServiceImpl implements HistoryService {

    private HistoryDAOImpl dao;

    public HistoryServiceImpl() {
        dao = new HistoryDAOImpl();
    }

    @Override
    public Integer save(History history) {
        return dao.save(history);
    }

    @Override
    public History getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(History history) {
        return dao.update(history);
    }

    @Override
    public boolean delete(History history) {
        return false;
    }

    @Override
    public ObservableList<History> getAll() {
        return dao.getAll();
    }

    public ObservableList<History> getByClient(Client client){
        return dao.getByClient(client);
    }

    public boolean saveCommentByClient(Client client, String comment){
        return dao.saveCommentByClient(client, comment);
    }
}
