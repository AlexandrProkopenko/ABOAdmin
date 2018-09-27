package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.Client;

public interface ClientService {

    Integer save(Client client);

    Client getById(Integer id);

    boolean update(Client client);

    boolean delete(Client client);

    ObservableList<Client> getAll();
}
