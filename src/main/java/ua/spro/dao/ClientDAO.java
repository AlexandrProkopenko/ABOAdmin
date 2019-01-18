package ua.spro.dao;

import javafx.collections.ObservableList;
import ua.spro.entity.User;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;

import java.time.LocalDateTime;

public interface ClientDAO {

    Integer save(Client client);

    Client getById(Integer id);

    boolean update(Client client);

    boolean delete(Client client);

    ObservableList<Client> getAll();

    boolean saveClientAndHistory(Client client, History history);

    ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department);

    boolean setStatusToClient(Client client, Status newStatus);

    boolean setDepartmentToClient(Client client, Department newDepartment);

    ObservableList<Client> getClientsByStatusDepStartEndAuthorExecutor(
            Status status, Department department, LocalDateTime startDate, LocalDateTime endDate, User author, User executor);
}
