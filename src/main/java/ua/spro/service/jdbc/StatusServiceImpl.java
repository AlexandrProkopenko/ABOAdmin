package ua.spro.service.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.jdbc.StatusDAOImpl;
import ua.spro.entity.client.Status;
import ua.spro.service.StatusService;

public class StatusServiceImpl implements StatusService {

    private StatusDAOImpl dao;


    public StatusServiceImpl() {
        dao = new StatusDAOImpl();
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
