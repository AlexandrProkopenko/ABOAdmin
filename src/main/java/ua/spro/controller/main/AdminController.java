package ua.spro.controller.main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ua.spro.ABOAdminApp;
import ua.spro.controller.MainController;
import ua.spro.entity.client.Client;
import ua.spro.entity.client.Department;
import ua.spro.entity.client.History;
import ua.spro.entity.client.Status;
import ua.spro.entity.User;
import ua.spro.entity.save.AdminSavedSettings;
import ua.spro.entity.save.FilterDate;
import ua.spro.entity.task.TaskExt;
import ua.spro.entity.task.TaskSelectType;
import ua.spro.model.admin.AdminModelInterface;
import ua.spro.model.user.UserModelInterface;
import ua.spro.util.tablecells.BooleanCell;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Formatter;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class AdminController {

    @FXML private AnchorPane rootAnchorPane;

//    управління задачами filtration clients by task parameters
    @FXML private ToggleButton tgbUndoneClient;
    @FXML private ToggleButton tgbDoneClient;
    @FXML private ToggleButton tgbAllClient;

    @FXML private ToggleButton tgbDay;
    @FXML private ToggleButton tgbWeek;
    @FXML private ToggleButton tgbMonth;

    @FXML private DatePicker dpTaskDateFrom;
    @FXML private DatePicker dpTaskDateTo;

    @FXML private CheckBox checkboxTask;
    @FXML private CheckBox checkboxSetTask;

    @FXML private ChoiceBox<User> chbTaskAuthor;
    @FXML private ChoiceBox<User> chbTaskExecutor;

//    set task parameters

    @FXML private CheckBox chekboxSetTask;
    @FXML private DatePicker dpSetTaskDateTo;
    @FXML private ChoiceBox<User> chbSetTaskExecutor;
// filtration tasks of current client
    @FXML private ToggleButton tgbUndoneTask;
    @FXML private ToggleButton tgbDoneTask;
    @FXML private ToggleButton tgbAllTask;

    private LocalDate dateFrom;
    private LocalDate dateTo;
//    table with tasks
    @FXML private TableView<TaskExt> tblViewTasks;
    @FXML private TableColumn<TaskExt, Void> clmnTasksNum;
    @FXML private TableColumn<TaskExt, LocalDateTime> clmnTasksDateTo;
    @FXML private TableColumn<TaskExt, String> clmnTasksComment;
    @FXML private TableColumn<TaskExt, Integer> clmnTasksAuthor;
    @FXML private TableColumn<TaskExt, Integer> clmnTasksExecutor;
    @FXML private TableColumn<TaskExt, Boolean> clmnTasksDone;


    //поля вводу данних нового користувача
    @FXML private TextField fldChildName;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField fldParentName;
    @FXML private TextField fldPhone;
    @FXML private TextField fldLocation;
    //поле вводу нового коментара
    @FXML private TextArea txtAreaNewComment;
    // таблиця клієнтів і її колонки

    @FXML private TableView<Client> tblViewClients;
    @FXML private TableColumn<Client, Void> clmnContactsId;
    @FXML private TableColumn<Client, String> clmnContactsChildName;
    @FXML private TableColumn<Client, Double> clmnContactsAge;
    @FXML private TableColumn<Client, LocalDate> clmnContactsBirthday;
    @FXML private TableColumn<Client, String> clmnContactsParentName;
    @FXML private TableColumn<Client, String> clmnContactsPhone;
    @FXML private TableColumn<Client, String> clmnContactsLocation;
    //    @FXML private TableColumn<Client, Integer> clmnContactsDepartment;
    @FXML private TableColumn<Client, Integer> clmnContactsDepartment;
    @FXML private TableColumn<Client, Integer> clmnContactsStatus;

    //таблиця історії коментарів і її колонки

    @FXML private TableView<History> tblViewHistories;
    @FXML private TableColumn<History, LocalDateTime> clmnHistoriesDate;
    @FXML private TableColumn<History, String> clmnHistoriesComment;
    @FXML private TableColumn<History, Integer> clmnHistoriesAuthor;
    //чойз бокси фільтрації за статусом і присвоєння нового статусу

    @FXML private ChoiceBox<Status> chbStatuses;
    @FXML private ChoiceBox<Status> chbSetStatus;


    @FXML private ChoiceBox<Department> chbDepartments;
    @FXML private ChoiceBox<Department> chbSetDepartment;

    @FXML private ImageView imViewLogo;
    @FXML private Button btn;

    private AdminModelInterface adminModel;

    private Client currentClient;
    private String newComment;
    private Status currentStatus;
    private Status filterStatus;
    private Department currentDepartment;
    private Department filterDepartment;
    private History currentHistory;


    private ObservableList<Client> selectedClients;
    private ObservableList<Client> clientsList;
    private ObservableList<Status> statusesList;
    private ObservableList<History> historiesList;
    private ObservableList<Department> departmentsList;
    private ObservableList<TaskExt> taskExtsList;

    private Status newStatus;
    private Department newDepartment;
    private Tooltip currentTooltip;

    private UserModelInterface userModel;
    private User currentUser;
    private ObservableList<User> users;

    private TaskSelectType taskSelectType;
    private TaskSelectType taskSelectTypeClient;
    private User authorFilter;
    private User executorFilter;
    private boolean datePickerFlag = false;
    private boolean choiseboxFlag = false;

    private AdminSavedSettings currentAdminSavedSettings;

    private MainController mainController;
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void initialize(){
            adminModel = ABOAdminApp.getAdminModel();
            userModel = ABOAdminApp.getUserModel();
            currentUser = userModel.getCurrentUser();
            users = userModel.getAllUsers();

        dataSetup();
        clientTableSetup();
        historyTableSetup();
        choiseboxesSetup();
        loadLogo();
        taskControlsInit();
        loadSettings();


        ABOAdminApp.adminController = this;
        System.out.println("setUp compleeted!");
    }

    public void saveSettings(){
        if(chbStatuses.getValue()!= null)
        currentAdminSavedSettings.setStatusId(chbStatuses.getValue().getStatusId());
        if(chbDepartments.getValue()!=null)
        currentAdminSavedSettings.setDepartmentId(chbDepartments.getValue().getDepartmentId());

        currentAdminSavedSettings.setCheckboxTasks(checkboxTask.isSelected());
        if(chbTaskAuthor.getValue()!=null)
        currentAdminSavedSettings.setAuthorId(chbTaskAuthor.getValue().getUserId());
        if(chbTaskExecutor.getValue()!=null)
        currentAdminSavedSettings.setExecutorId(chbTaskExecutor.getValue().getUserId());

        currentAdminSavedSettings.setDateFrom(dpTaskDateFrom.getValue());
        currentAdminSavedSettings.setDateTo(dpTaskDateTo.getValue());
        currentAdminSavedSettings.setClientSelectType(taskSelectTypeClient);

        if(tgbDay.isSelected()){
            currentAdminSavedSettings.setFilterDate(FilterDate.DAY);
        }else if(tgbWeek.isSelected()){
            currentAdminSavedSettings.setFilterDate(FilterDate.WEEK);
        }else if(tgbMonth.isSelected()){
            currentAdminSavedSettings.setFilterDate(FilterDate.MONTH);
        }else {
            currentAdminSavedSettings.setFilterDate(FilterDate.NONE);
        }



        currentAdminSavedSettings.setCheckboxNewTask(checkboxSetTask.isSelected());
        if(chbSetDepartment.getValue()!=null)
        currentAdminSavedSettings.setSetDepartmentId(chbSetDepartment.getValue().getDepartmentId());
        if(chbSetStatus.getValue()!= null)
        currentAdminSavedSettings.setSetStatusId(chbSetStatus.getValue().getStatusId());
        if(chbSetTaskExecutor.getValue()!=null)
        currentAdminSavedSettings.setSetTaskExecutor(chbSetTaskExecutor.getValue().getUserId());
        currentAdminSavedSettings.setTaskSelectType(taskSelectType);
        System.out.println(currentAdminSavedSettings);
    }

    private void loadSettings(){
        if (currentUser != null){
            currentAdminSavedSettings = currentUser.getSavedSettings().getAdminSavedSettings();
        }else {
            currentAdminSavedSettings = new AdminSavedSettings();
        }

        System.out.println("Loading settings for current user\n");
        System.out.println(currentAdminSavedSettings + "\n");


        filterStatus = statusesList.get(currentAdminSavedSettings.getStatusId()-1);
        filterDepartment = departmentsList.get(currentAdminSavedSettings.getDepartmentId() -1);
        executorFilter = users.get(currentAdminSavedSettings.getExecutorId()-1);
        authorFilter = users.get(currentAdminSavedSettings.getAuthorId()-1);

        checkboxTask.setSelected(currentAdminSavedSettings.getCheckboxTasks());
        activateTaskControls();

        taskSelectTypeClient = currentAdminSavedSettings.getClientSelectType();
        switch(taskSelectTypeClient){
            case UNDONE:
                tgbUndoneClient.setSelected(true);
                break;
            case DONE:
                tgbDoneClient.setSelected(true);
                break;
            case ALL:
                tgbAllClient.setSelected(true);
                break;
        }

        dateFrom = currentAdminSavedSettings.getDateFrom();
        dateTo = currentAdminSavedSettings.getDateTo();
        dpTaskDateFrom.setValue(dateFrom);
        dpTaskDateTo.setValue(dateTo);


        switch (currentAdminSavedSettings.getFilterDate()){
            case DAY:
                tgbDay.setSelected(true);
                break;
            case WEEK:
                tgbWeek.setSelected(true);
                break;
            case MONTH:
                tgbMonth.setSelected(true);
                break;
            case NONE:
                break;
        }
        taskSelectType = currentAdminSavedSettings.getTaskSelectType();
        switch(taskSelectType){
            case UNDONE:
                tgbUndoneTask.setSelected(true);
                break;
            case DONE:
                tgbDoneTask.setSelected(true);
                break;
            case ALL:
                tgbAllTask.setSelected(true);
                break;
        }
        checkboxSetTask.setSelected(currentAdminSavedSettings.getCheckboxNewTask());

        System.out.println("before chb Task Author set Value");
        choiseboxFlag = true;
        chbTaskAuthor.setValue(authorFilter);
        System.out.println("After chb Task Author set Value");
        choiseboxFlag = true;
        chbTaskExecutor.setValue(executorFilter);


        chbStatuses.setValue(filterStatus);
        chbDepartments.setValue(filterDepartment);

        System.out.println("get clients by filters after loading controlls state");
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
    }

    private void taskControlsInit(){
        tgbDay.setSelected(true);
        dateFrom = dateTo = LocalDate.now();
        dpTaskDateFrom.setValue(dateFrom);
        dpTaskDateTo.setValue(dateFrom);

        dpSetTaskDateTo.setValue(LocalDate.now().plusDays(1));

        System.out.println(currentUser);

        System.out.println(userModel.getCurrentUser());
        System.out.println(users);
        System.out.println("Index of current user " + users.indexOf(currentUser));



        chbTaskAuthor.setItems(users);

        chbTaskAuthor.setValue(users.get(currentUser.getUserId()-1));
        authorFilter = chbTaskAuthor.getValue();

        chbTaskExecutor.setItems(users);
        chbTaskExecutor.setValue(users.get(currentUser.getUserId()-1));
        executorFilter = chbTaskExecutor.getValue();

//        chbTaskExecutor.setL
        chbSetTaskExecutor.setValue(users.get(currentUser.getUserId()-1));
        chbSetTaskExecutor.setItems(users);

        tblViewTasksSetup();

        System.out.println("getvalue " + chbTaskExecutor.getValue() );
        taskChBoxesSetup();
        activateTaskControls();

        taskSelectType = TaskSelectType.UNDONE;
        taskSelectTypeClient = TaskSelectType.UNDONE;
    }

    private void tblViewTasksSetup(){
        //звязування колонок таблиці з класами
        clmnTasksNum.setCellValueFactory(new PropertyValueFactory<TaskExt, Void>("№"));
        clmnTasksNum.setCellFactory(col -> new TableCell<TaskExt, Void>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index+1));
                }
            }
        });

//        clmnTasksDateTo.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        clmnTasksDateTo.setCellValueFactory(new PropertyValueFactory<TaskExt, LocalDateTime>("endDate"));
        clmnTasksDateTo.setCellFactory(col -> new TableCell<TaskExt, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                Formatter formatter = new Formatter();

                if (empty)
                    setText(null);
                else {
                    formatter.format("%tF", item);
                    setText(formatter.toString());
                    formatter.close();
                }
            }
        });


        clmnTasksComment.setCellValueFactory(new PropertyValueFactory<TaskExt, String>("comment"));
        clmnTasksComment.setCellFactory(TextFieldTableCell.forTableColumn());

        clmnTasksAuthor.setCellValueFactory(new PropertyValueFactory<TaskExt, Integer>("authorId"));

        clmnTasksAuthor.setCellFactory(column -> {
            return new TableCell<TaskExt, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : users.get(item-1).getLogin());
                }
            }; });
        clmnTasksExecutor.setCellValueFactory(new PropertyValueFactory<TaskExt, Integer>("executorId"));
        clmnTasksExecutor.setCellFactory(column -> {
            return new TableCell<TaskExt, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : users.get(item-1).getLogin());
                }
            };
        }
            );

//        clmnTasksDone.setCellValueFactory(new PropertyValueFactory<TaskExt, Boolean>("done"));
//        clmnTasksDone.setCellFactory(column -> {return new BooleanCell();});


        Callback<TableColumn<TaskExt, Boolean>, TableCell<TaskExt, Boolean>> booleanCellFactory =
                new Callback<TableColumn<TaskExt, Boolean>, TableCell<TaskExt, Boolean>>() {
                    @Override
                    public TableCell<TaskExt, Boolean> call(TableColumn<TaskExt, Boolean> p) {
                        return new BooleanCell();
                    }
                };
        clmnTasksDone.setCellValueFactory(new PropertyValueFactory<TaskExt, Boolean>("done"));
        clmnTasksDone.setCellFactory(booleanCellFactory);
        clmnTasksDone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TaskExt, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TaskExt, Boolean> event) {
                Boolean newValue = event.getNewValue();
                TaskExt taskExt = tblViewTasks.getSelectionModel().getSelectedItem();
                adminModel.updateTaskDone(taskExt, newValue, currentUser, currentClient);
                adminModel.getTasksForCurrentHistoriesList(taskSelectType);
            }
        });

        tblViewTasks.setItems(taskExtsList);
    }

    private void taskChBoxesSetup(){
        chbTaskAuthor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("chbTaskAuthor on action");
                if(choiseboxFlag){
                    choiseboxFlag = false;
                    return;
                }
                authorFilter = chbTaskAuthor.getValue();
                adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
                saveSettings();
            }
        });

        chbTaskExecutor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("chbTaskExecutor on action");
                if(choiseboxFlag){
                    choiseboxFlag = false;
                    return;
                }
                executorFilter = chbTaskExecutor.getValue();
                adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
                saveSettings();
            }
        });


    }

    private void activateTaskControls(){
        if(checkboxTask.isSelected()){
            tgbDay.setDisable(false);
            tgbWeek.setDisable(false);
            tgbMonth.setDisable(false);
            tgbDoneClient.setDisable(false);
            tgbUndoneClient.setDisable(false);
            tgbAllClient.setDisable(false);
            dpTaskDateFrom.setDisable(false);
            dpTaskDateTo.setDisable(false);
            chbTaskAuthor.setDisable(false);
            chbTaskExecutor.setDisable(false);
        }else {
            tgbDay.setDisable(true);
            tgbWeek.setDisable(true);
            tgbMonth.setDisable(true);
            tgbDoneClient.setDisable(true);
            tgbUndoneClient.setDisable(true);
            tgbAllClient.setDisable(true);
            dpTaskDateFrom.setDisable(true);
            dpTaskDateTo.setDisable(true);
            chbTaskAuthor.setDisable(true);
            chbTaskExecutor.setDisable(true);
        }
    }

    private void setCurrents(){
        if(clientsList!=null) {
            if(!clientsList.isEmpty())
            currentClient = clientsList.get(0);
        }
        if(statusesList!=null){
            if(!statusesList.isEmpty())
            for (Status s : statusesList) {
                if (s.getClientStatus().equals("Всі")) {
                    currentStatus = s;
                }
            }
        }
        if(departmentsList!=null) {
            if(!departmentsList.isEmpty())
            for (Department dep : departmentsList) {
                if (dep.getClientDepartment().equals("Всі")) {
                    currentDepartment = dep;
                }
            }
        }
    }

    private void dataSetup(){
        clientsList = adminModel.getClientsList();
        statusesList = adminModel.getStatusesList();
        historiesList = adminModel.getHistoriesList();
        departmentsList = adminModel.getDepartmentsList();
        taskExtsList = adminModel.getTaskExtsList();
        setCurrents();
    }

    private void fillClientData(){
        tblViewClients.setItems(clientsList);
    }

    private void clientTableSetup(){
        //звязування колонок таблиці з класами
        clmnContactsId.setCellValueFactory(new PropertyValueFactory<Client, Void>("№"));
        clmnContactsId.setCellFactory(col -> new TableCell<Client, Void>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index+1));
                }
            }


        });

        clmnContactsChildName.setCellValueFactory(new PropertyValueFactory<Client, String>("childName"));
        clmnContactsChildName.setCellFactory(TextFieldTableCell.forTableColumn());
//        clmnContactsChildName.setCellFactory(column -> {
//            return new TableCell<Client, String>(){
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setText(empty ? "" : getItem().toString());
//                    setGraphic(null);
//                }
//            };
//        });

        clmnContactsChildName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newNameValue = event.getNewValue();
                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setChildName(newNameValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());
            }
        });
        clmnContactsChildName.prefWidthProperty().bind(tblViewClients.widthProperty().divide(6)); // w * 1/4 ширина

        clmnContactsAge.setCellValueFactory(new PropertyValueFactory<Client, Double>("age"));
        clmnContactsAge.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));
        clmnContactsAge.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, Double> event) {
                Double newAgeValue = event.getNewValue();

                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setAge(newAgeValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());

//                tblViewClients.refresh();
            }
        });
        clmnContactsBirthday.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("birthday"));
//            clmnContactsBirthday.setCellFactory(col -> new BirthdayCell());
        clmnContactsBirthday.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object.toString();
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string);
            }
        }));


        clmnContactsBirthday.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, LocalDate> event) {
                LocalDate newDateValue = event.getNewValue();

                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setBirthday(newDateValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());

//                tblViewClients.refresh();

            }
        });
        clmnContactsParentName.setCellValueFactory(new PropertyValueFactory<Client, String>("parentName"));
        clmnContactsParentName.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsParentName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newParentValue = event.getNewValue();

                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setParentName(newParentValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());
            }
        });
        clmnContactsParentName.prefWidthProperty().bind(tblViewClients.widthProperty().divide(6)); // w * 1/4 ширина

        clmnContactsPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        clmnContactsPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsPhone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newPhoneValue = event.getNewValue();

                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setPhone(newPhoneValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());

            }
        });
        clmnContactsLocation.setCellValueFactory(new PropertyValueFactory<Client, String>("location"));
        clmnContactsLocation.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsLocation.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newLocationValue = event.getNewValue();

                Client oldValue = tblViewClients.getSelectionModel().getSelectedItem();
                Client newValue = new Client(oldValue);
                newValue.setLocation(newLocationValue);
                adminModel.editClient(oldValue, newValue, userModel.getCurrentUser());

            }
        });

        clmnContactsDepartment.setCellValueFactory(new PropertyValueFactory<Client, Integer>("departmentId"));
        clmnContactsDepartment.setCellFactory(column->{
            return new TableCell<Client, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : departmentsList.get(item-1).getClientDepartment());
                    setGraphic(null);
                }
            };
        });

        clmnContactsStatus.setCellValueFactory(new PropertyValueFactory<Client, Integer>("statusId"));
        clmnContactsStatus.setCellFactory(column -> {
            return new TableCell<Client, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : statusesList.get(item-1).getClientStatus());
                    setGraphic(null);
                    TableRow<Client> currentRow = getTableRow();
                    if (!isEmpty()) {
                        tblViewClients.setStyle("-fx-background: rgba(254,82,60,0);");
                        switch (item) {
                            case 1:

                                currentRow.setStyle("-fx-control-inner-background: rgba(28,214,255,0.09);");
                                break;
                            case 2:
                                currentRow.setStyle("-fx-control-inner-background: rgba(22,165,255,0.14);");
                                break;
                            case 3:
                                currentRow.setStyle("-fx-control-inner-background: rgba(162,89,255,0.12);" );
                                break;
                            case 4:
                                currentRow.setStyle("-fx-control-inner-background: rgba(208,255,38,0.11);");
                                break;
                            case 5:
                                currentRow.setStyle("-fx-control-inner-background: rgba(23,255,20,0.18);");
                                break;
                            case 6:
                                currentRow.setStyle("-fx-control-inner-background: rgba(31,117,31,0.31);");
                                break;
                            case 7:
                                currentRow.setStyle("-fx-control-inner-background: rgba(199,106,18,0.24);");
                                break;
                            case 8:
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0);");
                                break;
                            case 9:
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0.71);");
                                break;
                            default:
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0);");
                        }
                    }
                }
            };

        });





        fillClientData();
        tblViewClients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        selectedClients = FXCollections.observableArrayList();
    }

    private void historyTableSetup(){
        clmnHistoriesDate.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        clmnHistoriesDate.setCellFactory(col -> new TableCell<History, LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                Formatter formatter = new Formatter();

                if (empty)
                    setText(null);
                else {
                    formatter.format("%tF", item);
                    setText(formatter.toString());
                    formatter.close();
                }
            }
        });

        clmnHistoriesComment.setCellValueFactory(new PropertyValueFactory<History, String>("comment"));
//        clmnHistoriesComment.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnHistoriesComment.setCellFactory(column -> {return new TableCell<History, String>() {
            private Text text;
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if ( text != null ) {
                    text.textProperty().unbind();
                }
                if ( empty || item == null ) {
                    setGraphic(null);
                } else {
                    if ( text == null ) {
                        text = new Text();
                        text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
                    }
                    text.textProperty().bind(itemProperty());
                    setGraphic(text);
                }
            }
        };});

        clmnHistoriesComment.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<History, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<History, String> event) {
                System.out.println("row value "+event.getRowValue());
                adminModel.editComment(
                        currentClient,
                        event.getRowValue(),
                        event.getOldValue(),
                        event.getNewValue(),
                        userModel.getCurrentUser()
                );

            }
        });

        clmnHistoriesAuthor.setCellValueFactory(new PropertyValueFactory<History, Integer>("userId"));
        clmnHistoriesAuthor.setCellFactory(column -> {
            return new TableCell<History, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
//                    System.out.println("Author cell: " + item);
//                    System.out.println(userModel);
//                    System.out.println(userModel.getAllUsers());
//                    System.out.println(userModel.getAllUsers().get(item-1));
//                    setText(empty ? "" : userModel.getAllUsers().get(item-1).getLogin());
//                    String text = userModel.getAllUsers().get(item).getLogin();
//                    System.out.println(text);
                    setText(empty ? "" : users.get(item-1).getLogin());
                }
        }; });


        currentTooltip = new Tooltip();
        tblViewHistories.setTooltip(currentTooltip);
        tblViewHistories.setEditable(true);
        clmnHistoriesComment.setEditable(true);
        tblViewHistories.setItems(historiesList);
    }

    private void fillChoiseBoxes(){


        chbStatuses.setItems(statusesList);
        chbSetStatus.setItems(statusesList);
        chbStatuses.setValue(currentStatus);


        chbStatuses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (chbStatuses.getSelectionModel().getSelectedItem() != null) {
                        Integer choice = chbStatuses.getSelectionModel().getSelectedItem().getStatusId();
                        String color;
                        if (choice != null) {
                            switch (choice) {
                                case 1:
                                    color = "-fx-control-inner-background: rgba(28,214,255,0.09);";
                                    break;
                                case 2:
                                    color = "-fx-control-inner-background: rgba(22,165,255,0.14);";
                                    break;
                                case 3:
                                    color = "-fx-control-inner-background: rgba(162,89,255,0.12);";
                                    break;
                                case 4:
                                    color = "-fx-control-inner-background: rgba(208,255,38,0.11);";
                                    break;
                                case 5:
                                    color = "-fx-control-inner-background: rgba(23,255,20,0.18);";
                                    break;
                                case 6:
                                    color = "-fx-control-inner-background: rgba(31,117,31,0.31);";
                                    break;
                                case 7:
                                    color = "-fx-control-inner-background: rgba(199,106,18,0.24);";
                                    break;
                                case 8:
                                    color = "-fx-control-inner-background: rgba(254,82,60,0.71);";
                                    break;
                                case 9:
                                    color = "-fx-control-inner-background: rgba(254,82,60,0);";
                                    break;
                                default:
                                    color = "";
                                    break;
                            }

                            chbStatuses.setStyle(color);
//                            text.setFill(Color.web(color));
                        }
                    }
                }
        );


        chbDepartments.setItems(departmentsList);
        chbSetDepartment.setItems(departmentsList);
        chbDepartments.setValue(currentDepartment);



    }

    private void choiseboxesSetup(){
        fillChoiseBoxes();

        //дії чойз боксів при виборі елемента

        chbStatuses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filterStatus = chbStatuses.getValue();
                if(checkboxTask.isSelected()) {
                    adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
                }else {
                    adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);
                }
                saveSettings();

            }
        });

        chbSetStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newStatus = chbSetStatus.getValue();
                btnSetStatusOnAction();
                saveSettings();
            }
        });


        chbDepartments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filterDepartment = chbDepartments.getValue();
                if(checkboxTask.isSelected()) {
                    adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
                }else {
                    adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);
                }
                saveSettings();
            }
        });

        chbSetDepartment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newDepartment = chbSetDepartment.getValue();
                btnSetDepartmentOnAction();
                saveSettings();
            }
        });


        txtAreaNewComment.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
//                    System.out.println(event.getCode());
//                    event.get
                if(event.getCode() == KeyCode.ENTER ){

                    btnAddHistoryOnAction();
                }
            }
        });


    }

    private void loadLogo(){
        Image logo = new Image("/ua/spro/images/logo.jpg");
        imViewLogo.setImage(logo);
        imViewLogo.setVisible(true);
    }



    private void chbDepartmentsOnAction(){

        currentDepartment = chbDepartments.getValue() ;
        adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);

    }

    private void chbStatusesOnAction(){

        currentStatus = chbStatuses.getValue() ;
        adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);

    }

    public void btnSaveContactOnAction(){
        Client newClient;
        String name = fldChildName.getText();
        LocalDate birthday = dpBirthday.getValue();
        String parentName = fldParentName.getText();
        String phone = fldPhone.getText();
        String location = fldLocation.getText();
        Status newStatus = chbSetStatus.getValue();
        Department newDepartment = chbSetDepartment.getValue();
        Integer statusId = 1;
        Integer departmentId = 1;
        if(newStatus!= null){
            statusId = newStatus.getStatusId();
        }
        System.out.println(newStatus!= null);
        if(newDepartment!=null){
            departmentId = newDepartment.getDepartmentId();
        }
        System.out.println("button save client");
        if(name.equals("")||
                birthday == null ||
                parentName.equals("")||
                phone.equals("")||
                location.equals("")){
            System.out.println("alert");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Неправильні дані");
            alert.initOwner(mainStage);
            alert.setHeaderText("Заповніть всі дані, будь ласка!");
            alert.showAndWait();
        }else {
            System.out.println("new client");
            System.out.println("statusid " + statusId + "   departmentId " + departmentId);
            newClient = new Client(name,birthday, parentName, phone, location,  departmentId, statusId);

            adminModel.saveClient(newClient, userModel.getCurrentUser());
            adminModel.getClientsByStatusAndDepartment(newStatus, newDepartment);
//            clientService.save(newClient);
//            clientsList = clientService.getAll();
//            tblViewClients.setItems(clientsList);

            fldChildName.clear();
            fldLocation.clear();
            fldParentName.clear();
            fldPhone.clear();
        }
    }

    public void btnAddHistoryOnAction(){
       String newComment = txtAreaNewComment.getText();
           if (!newComment.equals("") && currentClient != null) {
               if(!chekboxSetTask.isSelected()) {
               adminModel.addComment(currentClient, newComment, userModel.getCurrentUser());
               }else {
                   System.out.println("Create new Task");
                   User executor = chbSetTaskExecutor.getValue();
                   LocalDate dateTo = dpSetTaskDateTo.getValue();
                   if(executor != null && dateTo != null) {
                       adminModel.addTask(currentClient, newComment, userModel.getCurrentUser(), executor, dateTo);
                   }else {
                       System.out.println("Fill all inputs!");
                   }
               }
           }
        txtAreaNewComment.clear();

    }

    public void tblViewClientsOnMouseClicked(){
        selectedClients = tblViewClients.getSelectionModel().getSelectedItems() ;
        if(selectedClients!=null) {

            if(selectedClients.size()>1){
                historiesList.clear();
            }else {
                if(currentClient == selectedClients.get(0)) return;

                currentClient = selectedClients.get(0);
                if (currentClient != null) {
                    adminModel.getHistoriesByClient(currentClient);
                    adminModel.getTasksForCurrentHistoriesList(taskSelectType);
                    currentHistory = historiesList.get(0);

                    if (currentHistory != null) {
                        currentTooltip.setText(currentHistory.getComment());
                    }
                }
            }
        }
    }

    public void btnSetStatusOnAction(){
        if(selectedClients == null || selectedClients.size() == 0) return;

        if(selectedClients != null && newStatus != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String names = clientsArrayToString();
            alert.setTitle("Змінити статус для " + names);
            alert.initOwner(mainStage);
            alert.setHeaderText("Змінити статус \n"+ names + "з " +
                    statusesList.get( selectedClients.get(0).getStatusId() - 1 ).getClientStatus() + "\n на  " + newStatus);
            System.out.println(selectedClients.get(0).getStatusId() );

            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){


                adminModel.setStatus(
                        selectedClients,
                        newStatus,
                        userModel.getCurrentUser()
                );
                adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        tblViewClients.requestFocus();
                        tblViewClients.getSelectionModel().select(currentClient);
                        tblViewClients.getFocusModel().focus(tblViewClients.getSelectionModel().getSelectedIndex());

                    }
                });
                tblViewClients.getSelectionModel().select(currentClient);
            }else {
            }
        }
        saveSettings();
    }

    private String clientsArrayToString(){
        StringBuilder sb = new StringBuilder();
        for(Client client: selectedClients){
            sb.append(client.getChildName()+ " \n");
        }
        return sb.toString();
    }

    public void btnSetDepartmentOnAction(){
        if(selectedClients == null || selectedClients.size() == 0) return;
        if(selectedClients != null && newDepartment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String names = clientsArrayToString();
            alert.setTitle("Змінити філію для " + names);
            alert.initOwner(mainStage);
            alert.setHeaderText("Змінити філію для \n"+ names + "\n на  " + newDepartment);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){

                adminModel.setDepartment(
                        selectedClients,
                        newDepartment,
                        userModel.getCurrentUser()
                );
                adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);
                tblViewClients.getSelectionModel().select(currentClient);
            }else {

            }
        }
        saveSettings();
    }

    public void ButtonOnAction() {

        System.out.println(currentUser);
        chbTaskAuthor.getSelectionModel().select(users.indexOf(currentUser));
//        chbTaskAuthor

    }

    public void tblViewHistoriesOnMouseClicked(){
        currentHistory = tblViewHistories.getSelectionModel().getSelectedItem() ;
        if (currentHistory!=null)
            currentTooltip.setText(currentHistory.getComment());
    }

    public void tblViewHistoriesOnMouseEntered(){

    }

    public void tblViewHistoriesOnMouseExited(){

    }

    public void tblViewClientsOnKeyTyped(){
        System.out.println(tblViewClients.getFocusModel().getFocusedItem());
    }



    public void importFromExcel(){
        adminModel.reloadLists();
        setCurrents();
        setChoiseBoxesValues();
    }

    public void changeConnection(){
        adminModel.reloadLists();
        setCurrents();
        setChoiseBoxesValues();
        userModel.exit();
    }

    private void setChoiseBoxesValues(){
        chbStatuses.setValue(currentStatus);
        chbDepartments.setValue(currentDepartment);
    }

    public void newCommentOnKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER ){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    txtAreaNewComment.positionCaret(0);
                }
            });
            btnAddHistoryOnAction();
        }
    }

    public void fldChildNameOnKeyPressed(KeyEvent event){

    }

    public void gridNewClientOnKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER ){
            System.out.println("Enter pressed");
            btnSaveContactOnAction();
        }
    }




    public void tgbAllClientOnAction(ActionEvent event) {
        taskSelectTypeClient = TaskSelectType.ALL;
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }


    public void tgbDoneClientOnAction(ActionEvent event) {
        taskSelectTypeClient = TaskSelectType.DONE;
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }


    public void tgbUndoneClientOnAction(ActionEvent event) {
        taskSelectTypeClient = TaskSelectType.UNDONE;
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }

    public void tgbAllTaskOnAction(ActionEvent event) {
        taskSelectType = TaskSelectType.ALL;
        if(!historiesList.isEmpty()) {
            adminModel.getTasksForCurrentHistoriesList(taskSelectType);
        }
        saveSettings();
    }


    public void tgbDoneTaskOnAction(ActionEvent event) {
        taskSelectType = TaskSelectType.DONE;
        if(!historiesList.isEmpty()) {
            adminModel.getTasksForCurrentHistoriesList(taskSelectType);
        }
    }


    public void tgbUndoneTaskOnAction(ActionEvent event) {
        taskSelectType = TaskSelectType.UNDONE;
        if(!historiesList.isEmpty()) {
            adminModel.getTasksForCurrentHistoriesList(taskSelectType);
        }
        saveSettings();
    }


    public void tgbDayOnAction() {
        dateFrom = dateTo = LocalDate.now();
        datePickerFlag = true;
        dpTaskDateFrom.setValue(dateFrom);
        datePickerFlag = true;
        dpTaskDateTo.setValue(dateFrom);
//        get  new info
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }


    public void tgbWeekOnAction() {
        LocalDate now = LocalDate.now();
        dateFrom = now.minusDays( (now.getDayOfWeek().getValue() -1) );
        dateTo = now.plusDays( (7 - now.getDayOfWeek().getValue())  ) ;
        datePickerFlag = true;
        dpTaskDateFrom.setValue(dateFrom);
        datePickerFlag = true;
        dpTaskDateTo.setValue(dateTo);
        //        get  new info
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }


    public void tgbMonthOnAction() {
        LocalDate now = LocalDate.now();
        dateFrom = now.withDayOfMonth(1);
        dateTo = now.with(lastDayOfMonth());
        datePickerFlag = true;
        dpTaskDateFrom.setValue(dateFrom);
        datePickerFlag = true;
        dpTaskDateTo.setValue(dateTo);
        //        get  new info
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }

    public void dpTaskDateFromOnAction(ActionEvent event) {
        System.out.println("DateFrom on action");
        dateFrom = dpTaskDateFrom.getValue();
        if(datePickerFlag){
            datePickerFlag = false;
            return;
        }
//        System.out.println(event.);
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }

    public void dpTaskDateToOnAction(ActionEvent event) {
        System.out.println("DateTo on action");
        dateTo = dpTaskDateTo.getValue();
        if(datePickerFlag){
            datePickerFlag = false;
            return;
        }
        adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        saveSettings();
    }

    public void checkboxTaskOnAction(ActionEvent event){
        activateTaskControls();
        if(checkboxTask.isSelected()){
            adminModel.getClientsByFilters(filterStatus, filterDepartment, dateFrom, dateTo,taskSelectTypeClient, authorFilter, executorFilter);
        }else {
            adminModel.getClientsByStatusAndDepartment(filterStatus, filterDepartment);
        }
        saveSettings();
    }

}
