package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.Status;

public interface StatusDAO {

    boolean save(Status clientStatus);

    Status getById(Integer id);

    boolean update(Status clientStatus);

    boolean delete(Status clientStatus);

    ObservableList<Status> getAll();
}
