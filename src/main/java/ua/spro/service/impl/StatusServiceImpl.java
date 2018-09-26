package ua.spro.service.impl;

import javafx.collections.ObservableList;
import ua.spro.dao.StatusDAO;
import ua.spro.dao.jdbc.StatusDAOImpl;
import ua.spro.entity.Status;
import ua.spro.service.StatusService;

import java.util.Observable;

public class StatusServiceImpl implements StatusService {

    private StatusDAOImpl dao;


    public StatusServiceImpl(Observable observable) {
        dao = new StatusDAOImpl(observable);
    }

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
        return dao.getAll();
    }

    public Integer getIdByClientStatus(String clientStatus){
      return   dao.getIdByClientStatus(clientStatus);
    }
}
