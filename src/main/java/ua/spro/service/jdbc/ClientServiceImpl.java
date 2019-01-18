package ua.spro.service.jdbc;

import javafx.collections.ObservableList;
import ua.spro.dao.jdbc.ClientDAOImpl;
import ua.spro.entity.User;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.service.ClientService;

import java.time.LocalDateTime;

public class ClientServiceImpl implements ClientService {

    private ClientDAOImpl dao;

    public ClientServiceImpl() {
        dao = new ClientDAOImpl();
    }

    @Override
    public boolean clearTable(){
        return dao.clearTable();
    }

    @Override
    public boolean saveClientAndHistory(Client client, History history){
        return dao.saveClientAndHistory(client, history);
    }

    @Override
    public Integer save(Client client) {
        return dao.save(client);
    }

    @Override
    public Client getById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Client client) {
        return dao.update(client);
    }

    @Override
    public boolean delete(Client client) {
        return false;
    }

    @Override
    public ObservableList<Client> getAll() {
        return dao.getAll();
    }

    public ObservableList<Client> getClientsByStatus(Status status){
        return dao.getClientsByStatus(status);
    }

    @Override
    public boolean setStatusToClient(Client client, Status newStatus){
        return dao.setStatusToClient(client, newStatus);
    }

    public ObservableList<Client> getClientsByDepartment(Department department){
        return dao.getClientsByDepartment(department);
    }
    @Override
    public boolean setDepartmentToClient(Client client, Department newDepartment){
        return dao.setDepartmentToClient(client, newDepartment);
    }
    @Override
    public ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department){
        return dao.getClientsByStatusAndDepartment(status, department);
    }

    @Override
    public ObservableList<Client> getClientsByStatusDepStartEndAuthorExecutor(Status status, Department department, LocalDateTime startDate, LocalDateTime endDate, User author, User executor) {
        return dao.getClientsByStatusDepStartEndAuthorExecutor(status, department, startDate, endDate, author, executor);
    }
}
