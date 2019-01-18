package ua.spro.model.admin;

import javafx.collections.ObservableList;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.entity.User;

public interface AdminModelInterface {

    boolean saveClient(Client newClient, User user);

    boolean editClient(Client oldValue, Client newValue, User user);

    boolean setDepartment(ObservableList<Client> selectedClients, Department newValue, User user);

    boolean setStatus(ObservableList<Client> selectedClients, Status newValue, User user);

    boolean addComment(Client client, String comment, User user);

    boolean editComment(Client client, History history, String oldValue, String newValue, User user);

    boolean testConnectionToDB(String url, String login, String password);

    void reloadLists();

    ObservableList<Client> getClientsList();

    ObservableList<Status> getStatusesList();

    ObservableList<Department> getDepartmentsList();

    ObservableList<History> getHistoriesList();



    boolean getClientsByStatusAndDepartment(Status status, Department department);

    boolean getHistoriesByClient(Client client);






}
