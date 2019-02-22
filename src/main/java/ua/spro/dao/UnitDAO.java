package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.unit.ConnectedUnit;

public interface UnitDAO {


    Integer save(ConnectedUnit unit);

    boolean delete(ConnectedUnit unit);

    boolean newInfoAdded(ConnectedUnit unit);

    boolean unitIsUpdated(ConnectedUnit unit);

    ObservableList<ConnectedUnit> getAll();

    ConnectedUnit getById(Integer id);

    boolean needUpdate(ConnectedUnit unit);
}
