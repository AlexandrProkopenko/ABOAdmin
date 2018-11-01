package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.entity.Status;

public interface ClientService {

    Integer save(Client client);

    Client getById(Integer id);

    boolean update(Client client);

    boolean delete(Client client);

    ObservableList<Client> getAll();

    boolean saveClientAndHistory(Client client, History history);

    boolean clearTable();

    ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department);

    boolean setStatusToClient(Client client, Status newStatus);

    boolean setDepartmentToClient(Client client, Department newDepartment);
}
