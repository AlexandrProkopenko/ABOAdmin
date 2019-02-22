package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.unit.ConnectedUnit;

public interface UnitService {

    Integer save(ConnectedUnit unit);

    boolean delete(ConnectedUnit unit);

    boolean newInfoAdded(ConnectedUnit unit);

    boolean unitIsUpdated(ConnectedUnit unit);

    ObservableList<ConnectedUnit> getAll();

    ConnectedUnit getById(Integer id);

    boolean needUpdate(ConnectedUnit unit);
}
