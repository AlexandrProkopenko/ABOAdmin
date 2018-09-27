package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.Status;

public interface StatusService {

    boolean save(Status clientStatus);

    Status getById(Integer id);

    boolean update(Status clientStatus);

    boolean delete(Status clientStatus);

    ObservableList<Status> getAll();
}
