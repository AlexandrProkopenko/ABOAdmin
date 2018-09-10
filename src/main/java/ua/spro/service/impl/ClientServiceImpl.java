package ua.spro.service.impl;

import javafx.collections.ObservableList;
import ua.spro.dao.jdbc.ClientDAOImpl;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.entity.Status;
import ua.spro.service.ClientService;

public class ClientServiceImpl implements ClientService {

    private ClientDAOImpl dao;

    public ClientServiceImpl() {
        dao = new ClientDAOImpl();
    }

    public boolean testConnectionToDB(){
        return dao.testConnectionToDB();
    }

    public boolean clearTable(){
        return dao.clearTable();
    }

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

    public boolean setStatusToClient(Client client, Status newStatus){
        return dao.setStatusToClient(client, newStatus);
    }

    public ObservableList<Client> getClientsByDepartment(Department department){
        return dao.getClientsByDepartment(department);
    }

    public boolean setDepartmentToClient(Client client, Department newDepartment){
        return dao.setDepartmentToClient(client, newDepartment);
    }

    public ObservableList<Client> getClientsByStatusAndDepartment(Status status, Department department){
        return dao.getClientsByStatusAndDepartment(status, department);
    }
}
