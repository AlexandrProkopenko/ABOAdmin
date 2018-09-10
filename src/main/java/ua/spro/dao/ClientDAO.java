package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.Client;

public interface ClientDAO {

    Integer save(Client client);

    Client getById(Integer id);

    boolean update(Client client);

    boolean delete(Client client);

    ObservableList<Client> getAll();
}
