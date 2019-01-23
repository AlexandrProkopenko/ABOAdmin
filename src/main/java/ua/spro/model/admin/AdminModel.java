package ua.spro.model.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.entity.User;
import ua.spro.entity.task.Task;
import ua.spro.entity.task.TaskExt;
import ua.spro.entity.task.TaskSelectType;
import ua.spro.model.action.UserAction;
import ua.spro.model.action.actions.*;
import ua.spro.service.*;
import ua.spro.service.jdbc.*;
import ua.spro.util.ConnectionDBUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class AdminModel implements AdminModelInterface{

    //сервіси звернень до бази данних
    private ClientService clientService;
    private HistoryService historyService;
    private StatusService statusService;
    private DepartmentService departmentService;
    private TaskService taskService;


    //lists
    private ObservableList<Client> clientsList;
    private ObservableList<Status> statusesList;
    private ObservableList<History> historiesList;
    private ObservableList<Department> departmentsList;
    private ObservableList<TaskExt> taskExtsList;

//    empty data for default not null initialization
    private Client noClient;
    private Department noDepartment;
    private Status noStatus;
    private History noHistory;
    private TaskExt noTaskExt;

    private UserAction action;

    public AdminModel() {
        clientService = new ClientServiceImpl();
        historyService = new HistoryServiceImpl();
        statusService = new StatusServiceImpl();
        departmentService = new DepartmentServiceImpl();
        taskService = new TaskServiceImpl();


            clientsList = FXCollections.observableArrayList();
            historiesList = FXCollections.observableArrayList();
            statusesList = FXCollections.observableArrayList();
            departmentsList = FXCollections.observableArrayList();
            taskExtsList = FXCollections.observableArrayList();

        initializeNoData();
        if(ConnectionDBUtil.getInstance().isConnected()){
            reloadLists();
        }else {
            setEmptyDataToLists();
        }

    }

    private void initializeNoData(){
        noClient = new Client(1, "Нема даних", LocalDate.now(), "Нема даних", "-", "-", 1, 1);
        noStatus = new Status(1, "Нема даних");
        noDepartment = new Department(1, "Нема даних");
        noHistory = new History(1, LocalDateTime.now(),"-", 1);
        noTaskExt = new TaskExt(1, LocalDateTime.now(), 1, false, "noComments", 1);
    }

    private void setEmptyDataToLists(){
        clientsList.add(noClient);
        statusesList.add(noStatus);
        departmentsList.add(noDepartment);
        historiesList.add(noHistory);
        taskExtsList.add(noTaskExt);
    }

    public void reloadLists(){
        statusesList.clear();
        statusesList.addAll(statusService.getAll());
        departmentsList.clear();
        departmentsList.addAll(departmentService.getAll());
        historiesList.clear();

        taskExtsList.clear();


        clientsList.clear();
        clientsList.addAll(clientService.getAll());


    }



    @Override
    public boolean saveClient(Client newClient, User user) {
        System.out.println("AdminModel: save client");

        action = new CreateClientAction(newClient);
        History historyAction = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());
        clientService.saveClientAndHistory(newClient, historyAction);



        historiesList.clear();
        historiesList.add(historyAction);
        return false;
    }

    @Override
    public boolean editClient(Client oldValue, Client newValue, User user) {
        System.out.println("AdminModel: edit client");

        action = new EditClientAction(oldValue, newValue);
        History historyAction = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());

        clientService.update(newValue);
        historyService.save(historyAction);
        historyService.saveLink(newValue, historyAction);
        historiesList.add(historyAction);
        return false;
    }

    @Override
    public boolean setDepartment(ObservableList<Client> selectedClients, Department newValue, User user) {
        System.out.println("AdminModel: setDepartment");

        for (Client client: selectedClients){
            System.out.println(departmentsList);
            System.out.println((client.getDepartmentId()-1 ));
            action = new SetDepartmentAction(departmentsList.get( (client.getDepartmentId()-1 ) ), newValue);
            System.out.println("client old value " + client);
            client.setDepartmentId(newValue.getDepartmentId());
            System.out.println("client new value " + client);
            clientService.setDepartmentToClient(client, newValue);

            System.out.println(action.getDescription());
            History historyAction = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());
            historyAction.setId( historyService.save(historyAction) );
            historyService.saveLink(client, historyAction);
//            historiesList.addAll(historyAction);
        }
        if(selectedClients.size() == 1){
            historiesList.clear();
            historiesList.addAll(historyService.getByClient(selectedClients.get(0)));
        }

        return false;
    }

    @Override
    public boolean setStatus(ObservableList<Client> selectedClients, Status newValue, User user) {
        System.out.println("AdminModel: setStatus");


        for (Client client: selectedClients){
            action = new SetStatusAction(statusesList.get( (client.getStatusId()-1 ) ), newValue);
            System.out.println("client old value " + client);
            client.setStatusId(newValue.getStatusId());
            System.out.println("client new value " + client);
            clientService.setStatusToClient(client, newValue);

            System.out.println(action.getDescription());
            History historyAction = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());
            historyAction.setId( historyService.save(historyAction) );
            historyService.saveLink(client, historyAction);
//            historiesList.addAll(historyAction);
        }
        if(selectedClients.size() == 1){
            historiesList.clear();
            historiesList.addAll(historyService.getByClient(selectedClients.get(0)));
        }
        return false;
    }

    @Override
    public boolean addComment(Client client, String comment, User user) {
        System.out.println("AdminModel: addComment");
        action = new AddCommentAction(comment);
        History history = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());
        history.setId( historyService.save(history) );
        historyService.saveLink(client, history);
        historiesList.addAll(history);
        return false;
    }

    @Override
    public boolean addTask(Client client, String comment, User author, User executor, LocalDate dateTo) {
        System.out.println("AdminModel: addTask");
        action = new AddTaskAction(comment, dateTo, author, executor);
        History history = new History(LocalDateTime.now(), action.getDescription(), author.getUserId());
        history.setId( historyService.save(history) );
        historyService.saveLink(client, history);
        historiesList.addAll(history);

        Task task = new Task(LocalDateTime.of(dateTo, LocalTime.MIDNIGHT), executor.getUserId(), false);
        task.setId( taskService.save(task) );
        taskService.saveLink(history, task);
        TaskExt taskExt = new TaskExt(task.getId(), task.getEndDate(), executor.getUserId(), false, comment, author.getUserId());
        taskExtsList.addAll(taskExt);
        return false;
    }

    @Override
    public boolean editComment(Client client, History history, String oldValue, String newValue, User user) {
        System.out.println("AdminModel: edit Comment");
        action = new EditCommentAction(oldValue, newValue);
        System.out.println(action.getDescription());
        history.setComment(newValue);
        historyService.update(history);

        History historyAction = new History(LocalDateTime.now(), action.getDescription(), user.getUserId());
        historyAction.setId( historyService.save(historyAction) );
        historyService.saveLink(client, historyAction);
        historiesList.addAll(historyAction);
//        history.setId( historyService.save(history) );
//        historyService.saveLink(client, history);
        return false;
    }

    @Override
    public boolean testConnectionToDB(String url, String login, String password) {
       return ConnectionDBUtil.getInstance().testConnectionToDB(url, login, password);
    }


    @Override
    public boolean getClientsByStatusAndDepartment(Status status, Department department) {
        if (status == null) {
            for (Status s : statusesList) {
                if (s.getClientStatus().equals("Всі")) {
                    status = s;
                }
            }
        }

        if (department == null) {
            for (Department dep : departmentsList) {
                if (dep.getClientDepartment().equals("Всі")) {
                    department = dep;
                }
            }
        }
        if(status == null){
            status = noStatus;
        }
        if(department == null){
            department = noDepartment;
        }
        System.out.println("AdminModel:   get Clients by Status and Department");
        System.out.println("status: " + status.getClientStatus());
        System.out.println("department: " + department.getClientDepartment());

        clientsList.clear();
        clientsList.addAll(clientService.getClientsByStatusAndDepartment(status, department));
        return true;
    }

    @Override
    public boolean getClientsByFilters(Status status, Department department,
                                       LocalDate dateFrom, LocalDate dateTo, TaskSelectType taskSelectType, User author, User executor) {
        if (status == null) {
            for (Status s : statusesList) {
                if (s.getClientStatus().equals("Всі")) {
                    status = s;
                }
            }
        }

        if (department == null) {
            for (Department dep : departmentsList) {
                if (dep.getClientDepartment().equals("Всі")) {
                    department = dep;
                }
            }
        }
        if(status == null){
            status = noStatus;
        }
        if(department == null){
            department = noDepartment;
        }

        System.out.println("AdminModel:   get Clients by Filters");
        System.out.println("status: " + status.getClientStatus());
        System.out.println("department: " + department.getClientDepartment());
        System.out.println("dateFrom: " + dateFrom);
        System.out.println("dateTo: " + dateTo);
        System.out.println("taskSelectType: " + taskSelectType);
        System.out.println("author: " + author.getLogin());
        System.out.println("executor: " + executor.getLogin());

        clientsList.clear();
        clientsList.addAll(clientService.getClientsByFilters(status, department, dateFrom, dateTo, taskSelectType, author, executor));

        return false;
    }

    @Override
    public boolean getHistoriesByClient(Client client) {
        System.out.println("AdminModel: getHistoriesByClient");
        historiesList.clear();
        historiesList.addAll(historyService.getByClient(client));
        return true;
    }

    @Override
    public boolean getTasksForCurrentHistoriesList(TaskSelectType taskSelectType) {
        System.out.println("AdminModel: getHTasksByClient");
        taskExtsList.clear();
        taskExtsList.addAll( taskService.getByHistories(historiesList, taskSelectType) );
        return true;
    }

    @Override
    public boolean updateTaskDone(TaskExt taskExt, Boolean newValue, User author, Client client) {
        System.out.println("AdminModel: updateTaskDone");
        action = new EditTaskDoneAction(taskExt.getComment(), newValue);
        History historyAction = new History(LocalDateTime.now(), action.getDescription(), author.getUserId());

        System.out.println(taskExt.getId());

        taskService.updateDone(taskExt);
        historyService.save(historyAction);
        historyService.saveLink(client, historyAction);
        historiesList.add(historyAction);

        return false;
    }

    @Override
    public ObservableList<History> getHistoriesList() {
        return historiesList;
    }

    @Override
    public ObservableList<Client> getClientsList() {
        return clientsList;
    }

    @Override
    public ObservableList<Status> getStatusesList() {
        return statusesList;
    }

    @Override
    public ObservableList<Department> getDepartmentsList() {
        return departmentsList;
    }

    @Override
    public ObservableList<TaskExt> getTaskExtsList() {
        return taskExtsList;
    }
}
