package ua.spro.service;

import javafx.collections.ObservableList;
import ua.spro.entity.User;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.entity.task.TaskSelectType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClientService {

    Integer save(Client client);

    Client getById(Integer id);

    boolean update(Client client);

    boolean delete(Client client);

    ObservableList<Client> getAll();

    boolean saveClientAndHistory(Client client, History history);

    boolean clearTable();

    ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department);

    ObservableList<Client> getClientsByFilters(Status status, Department department,
                                LocalDate dateFrom, LocalDate dateTo, TaskSelectType taskSelectType, User author, User executor);
    boolean setStatusToClient(Client client, Status newStatus);

    boolean setDepartmentToClient(Client client, Department newDepartment);
}
