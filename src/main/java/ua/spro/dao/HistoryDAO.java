package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.History;

public interface HistoryDAO {

    Integer save(History history);

    History getById(Integer id);

    boolean update(History history);

    boolean delete(History history);

    ObservableList<History> getAll();

    boolean saveLink(Client client, History history);

    ObservableList<History> getByClient(Client client);
}
