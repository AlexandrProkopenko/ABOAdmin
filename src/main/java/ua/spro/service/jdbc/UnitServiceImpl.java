package ua.spro.service.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.UnitDAO;
import ua.spro.dao.jdbc.UnitDAOImpl;
import ua.spro.entity.unit.ConnectedUnit;
import ua.spro.service.UnitService;

public class UnitServiceImpl implements UnitService {

   private UnitDAO dao;

    public UnitServiceImpl() {
        this.dao = new UnitDAOImpl();
    }

    @Override
    public Integer save(ConnectedUnit unit) {
        return dao.save(unit);
    }

    @Override
    public boolean delete(ConnectedUnit unit) {
        return dao.delete(unit);
    }

    @Override
    public boolean newInfoAdded(ConnectedUnit unit) {
        return dao.newInfoAdded(unit);
    }

    @Override
    public boolean unitIsUpdated(ConnectedUnit unit) {
        return dao.unitIsUpdated(unit);
    }

    @Override
    public ObservableList<ConnectedUnit> getAll() {
        return dao.getAll();
    }

    @Override
    public ConnectedUnit getById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public boolean needUpdate(ConnectedUnit unit) {
        return dao.needUpdate(unit);
    }
}
