package ua.spro.controller.main;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ua.spro.controller.MainController;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.entity.Status;
import ua.spro.service.impl.ClientServiceImpl;
import ua.spro.service.impl.DepartmentServiceImpl;
import ua.spro.service.impl.HistoryServiceImpl;
import ua.spro.service.impl.StatusServiceImpl;
import ua.spro.util.ReadExcelUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;

public class AdminController {

    @FXML private AnchorPane rootAnchorPane;

    //поля вводу данних нового користувача
    @FXML
    private TextField fldChildName;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField fldParentName;
    @FXML private TextField fldPhone;
    @FXML private TextField fldLocation;
    //поле вводу нового коментара
    @FXML private TextArea txtAreaNewComment;
    // таблиця клієнтів і її колонки
    private ObservableList<Client> clientsList;
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
    private ObservableList<History> historiesList;
    @FXML private TableView<History> tblViewHistories;
    @FXML private TableColumn<History, LocalDateTime> clmnHistoriesDate;
    @FXML private TableColumn<History, String> clmnHistoriesComment;
    //чойз бокси фільтрації за статусом і присвоєння нового статусу
    private ObservableList<Status> statusesList;
    @FXML private ChoiceBox<Status> chbStatuses;
    @FXML private ChoiceBox<Status> chbSetStatus;

    private ObservableList<Department> departmentsList;
    @FXML private ChoiceBox<Department> chbDepartments;
    @FXML private ChoiceBox<Department> chbSetDepartment;

    @FXML private ImageView imViewLogo;
    @FXML private Button btn;

    //сервіси звернень до бази данних
    private ClientServiceImpl clientService;
    private HistoryServiceImpl historyService;
    private StatusServiceImpl statusService;
    private DepartmentServiceImpl departmentService;
    //локальні змінні класу
    private Client currentClient;
    private String newComment;
    private Status currentStatus;
    private Status newStatus;
    private Department currentDepartment;
    private Department newDepartment;
    private Tooltip currentTooltip;
    private History currentHistory;
    private ObservableList<Client> selectedClients;

    private Stage mainStage;
    private ReadExcelUtil excelUtil;

    private MainController mainController;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void showList(List list){
        for(Object o : list){
            System.out.println(o);
        }
    }

    private void fillClientData(){
        clientsList = clientService.getAll();
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
        clmnContactsChildName.setCellFactory(column -> {
            return new TableCell<Client, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Client> currentRow = getTableRow();
//                    currentRow.getTableView().get
//                    if (!isEmpty()) {
//
//                        if(item.equals("Маша"))
//                            currentRow.setStyle("-fx-background-color:lightcoral");
//                        else
//                            currentRow.setStyle("-fx-background-color:lightgreen");
//                    }
                }
            };
        });



        clmnContactsChildName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
//                    ((Client) event.getTableView().getItems().get(event.getTablePosition().getRow())).setChildName(event.getNewValue());
                String newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setChildName(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
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
                Double newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setAge(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
                tblViewClients.refresh();
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
                LocalDate newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setBirthday(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
                tblViewClients.refresh();

            }
        });
        clmnContactsParentName.setCellValueFactory(new PropertyValueFactory<Client, String>("parentName"));
        clmnContactsParentName.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsParentName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setParentName(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
            }
        });
        clmnContactsParentName.prefWidthProperty().bind(tblViewClients.widthProperty().divide(6)); // w * 1/4 ширина

        clmnContactsPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        clmnContactsPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsPhone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setPhone(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
            }
        });
        clmnContactsLocation.setCellValueFactory(new PropertyValueFactory<Client, String>("location"));
        clmnContactsLocation.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnContactsLocation.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> event) {
                String newValue = event.getNewValue();
                currentClient = tblViewClients.getSelectionModel().getSelectedItem();
                currentClient.setLocation(newValue);
                System.out.println(currentClient);
                clientService.update(currentClient);
            }
        });
        clmnContactsStatus.setCellValueFactory(new PropertyValueFactory<Client, Integer>("departmentId"));
        clmnContactsStatus.setCellFactory(column -> {
            return new TableCell<Client, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : statusesList.get(item-1).getClientStatus());
//                            setText(statusesList.get(item).getClientStatus());
                    setGraphic(null);

                    TableRow<Client> currentRow = getTableRow();
//                            System.out.println(item);

//                            System.out.println(getStyle());
//                            setBackground(Background.EMPTY);
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
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0.71);");
                                break;
                            case 9:
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0);");
                                break;
                            default:
                                currentRow.setStyle("-fx-control-inner-background: rgba(254,82,60,0);");
                        }

                    }

                }
            };

        });


        clmnContactsDepartment.setCellValueFactory(new PropertyValueFactory<Client, Integer>("statusId"));
        clmnContactsDepartment.setCellFactory(column->{
            return new TableCell<Client, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
//                    System.out.println( departmentsList.get(item).getClientDepartment());
//                    setText(empty ? "" : item.toString());
                    setText(empty ? "" : departmentsList.get(item-1).getClientDepartment());

                    setGraphic(null);

                }
            };
        });









        fillClientData();
        tblViewClients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedClients = FXCollections.observableArrayList();
        tblViewClients.setEditable(true);
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
        clmnHistoriesComment.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnHistoriesComment.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<History, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<History, String> event) {
                String newValue = event.getNewValue();
                currentHistory = tblViewHistories.getSelectionModel().getSelectedItem();
                currentHistory.setComment(newValue);
                System.out.println(currentHistory);
                historyService.update(currentHistory);
            }
        });
        currentTooltip = new Tooltip();
        tblViewHistories.setTooltip(currentTooltip);
        tblViewHistories.setEditable(true);

    }

    private void fillChoiseBoxes(){
        statusesList = statusService.getAll();
        chbStatuses.setItems(statusesList);
        chbSetStatus.setItems(statusesList);
        Integer statusId = statusService.getIdByClientStatus("Всі");
        if (statusId!= null)
            if (statusesList != null)
                if (statusesList.get(statusId - 1) != null) {
                    currentStatus = statusesList.get(statusId - 1);
                    chbStatuses.setValue(currentStatus);
                }

        chbStatuses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    Integer choice = chbStatuses.getSelectionModel().getSelectedItem().getStatusId() ;
                    String color;
                    if (choice != null) {
                        switch (choice){
                            case 1:
                                color = "-fx-control-inner-background: rgba(28,214,255,0.09);";
                                break;
                            case 2:
                                color = "-fx-control-inner-background: rgba(22,165,255,0.14);";
                                break;
                            case 3:
                                color ="-fx-control-inner-background: rgba(162,89,255,0.12);" ;
                                break;
                            case 4:
                                color ="-fx-control-inner-background: rgba(208,255,38,0.11);";
                                break;
                            case 5:
                                color ="-fx-control-inner-background: rgba(23,255,20,0.18);";
                                break;
                            case 6:
                                color ="-fx-control-inner-background: rgba(31,117,31,0.31);";
                                break;
                            case 7:
                                color ="-fx-control-inner-background: rgba(199,106,18,0.24);";
                                break;
                            case 8:
                                color ="-fx-control-inner-background: rgba(254,82,60,0.71);";
                                break;
                            case 9:
                                color ="-fx-control-inner-background: rgba(254,82,60,0);";
                                break;
                            default: color = "";
                                break;
                        }

                        chbStatuses.setStyle(color);
//                            text.setFill(Color.web(color));
                    }
                }
        );

        chbStatuses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });

//                chbStatuses.setStyle("-fx-control-inner-background: rgba(31,117,31,0.31);");

        departmentsList = departmentService.getAll();
        chbDepartments.setItems(departmentsList);
        chbSetDepartment.setItems(departmentsList);

        Integer departmentId = departmentService.getIdByClientDepartment("Всі");

        if (departmentId != null)
            if (departmentsList.get(departmentId - 1) != null) {
                currentDepartment = departmentsList.get(departmentId - 1);
                chbDepartments.setValue(currentDepartment);
            }

    }

    private void choiseboxesSetup(){
        fillChoiseBoxes();

        //дії чойз боксів при виборі елемента
        chbStatuses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chbStatusesOnAction();
            }
        });

        chbSetStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newStatus = chbSetStatus.getValue();
            }
        });


        chbDepartments.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chbDepartmentsOnAction();
            }
        });

        chbSetDepartment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newDepartment = chbSetDepartment.getValue();
            }
        });

        System.out.println(chbStatuses.getStylesheets());

        chbStatuses.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

                System.out.println( chbStatuses.getContextMenu().getStyleClass() );
            }
        });


        txtAreaNewComment.addEventHandler(new EventType<>(), new EventHandler<Event>() {
            @Override
            public void handle(Event event) {


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

    public void initialize(){

        clientService = new ClientServiceImpl();
        historyService = new HistoryServiceImpl();
        statusService = new StatusServiceImpl();
        departmentService = new DepartmentServiceImpl();
        excelUtil = new ReadExcelUtil(clientService, historyService, departmentService);
//        btn.setVisible(false);

        if(clientService.testConnectionToDB()) {
            clientTableSetup();
            historyTableSetup();
            choiseboxesSetup();
            loadLogo();
            System.out.println("setUp compleeted!");
        }

//        rootAnchorPane.setTopAnchor();

    }

    private void chbDepartmentsOnAction(){
        currentDepartment = chbDepartments.getValue();
        clientsList = clientService.getClientsByStatusAndDepartment(currentStatus, currentDepartment);
        tblViewClients.setItems(clientsList);
    }

    private void chbStatusesOnAction(){
        currentStatus = chbStatuses.getValue();
        clientsList = clientService.getClientsByStatusAndDepartment(currentStatus, currentDepartment);
        tblViewClients.setItems(clientsList);
    }

    public void btnSaveContactOnAction(){
        Client newClient;
        String name = fldChildName.getText();
        LocalDate birthday = dpBirthday.getValue();
        String parentName = fldParentName.getText();
        String phone = fldPhone.getText();
        String location = fldLocation.getText();
        newStatus = chbSetStatus.getValue();
        newDepartment = chbSetDepartment.getValue();
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
            clientService.save(newClient);
            clientsList = clientService.getAll();
            tblViewClients.setItems(clientsList);
            fldChildName.clear();
            fldLocation.clear();
            fldParentName.clear();
            fldPhone.clear();
        }
    }

    public void btnAddHistoryOnAction(){
        newComment = txtAreaNewComment.getText();
        if(!newComment.equals("") && currentClient!= null){
            historyService.saveCommentByClient(currentClient, newComment);
        }
        tblViewClientsOnMouseClicked();
        txtAreaNewComment.clear();
    }

    public void tblViewClientsOnMouseClicked(){
        selectedClients = tblViewClients.getSelectionModel().getSelectedItems();
        if(selectedClients!=null) {

            if(selectedClients.size()>1){
                tblViewHistories.setItems(FXCollections.observableArrayList());
            }else {
                currentClient = selectedClients.get(0);
                if (currentClient != null) {
                    historiesList = historyService.getByClient(currentClient);
                    tblViewHistories.setItems(historiesList);
                    currentHistory = historiesList.get(0);
                    if (currentHistory != null) {
                        currentTooltip.setText(currentHistory.getComment());
                    }
                }
            }
        }
    }

    public void btnSetStatusOnAction(){
        if(selectedClients != null && newStatus != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String names = clientsArrayToString();
            alert.setTitle("Змінити статус для " + names);
            alert.initOwner(mainStage);
            alert.setHeaderText("Змінити статус \n"+ names + "\n на  " + newStatus);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                for (Client client: selectedClients) {
                    clientService.setStatusToClient(client, newStatus);
                }
                chbStatusesOnAction();
            }else {
            }
        }
    }

    private String clientsArrayToString(){
        StringBuilder sb = new StringBuilder();
        for(Client client: selectedClients){
            sb.append(client.getChildName()+ " \n");
        }
        return sb.toString();
    }

    public void btnSetDepartmentOnAction(){
        if(selectedClients != null && newDepartment != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String names = clientsArrayToString();
            alert.setTitle("Змінити філію для " + names);
            alert.initOwner(mainStage);
            alert.setHeaderText("Змінити філію для \n"+ names + "\n на  " + newDepartment);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                for (Client client: selectedClients) {
                    clientService.setDepartmentToClient(client, newDepartment);
                }
                chbDepartmentsOnAction();
            }else {

            }
        }
    }

    public void ButtonOnAction() {

        System.out.println( chbStatuses.getStylesheets() );
        chbStatuses.onShowingProperty().addListener(new ChangeListener<EventHandler<Event>>() {
            @Override
            public void changed(ObservableValue<? extends EventHandler<Event>> observable, EventHandler<Event> oldValue, EventHandler<Event> newValue) {

            }
        });

//        clientService.clearTable();
//        excelUtil.readExcel();
//        currentDepartment = departmentService.getById(9);
//        currentStatus = statusService.getById(1);
//        clientTableSetup();
//        choiseboxesSetup();
//        historyTableSetup();

//
//        System.out.println( excelUtil.extractAge("8,5") );
//        System.out.println( excelUtil.extractAge("8.5") );
//        System.out.println( excelUtil.extractAge("8") );


//        tblViewClients.setItems(clientsList);
//        departmentsList = departmentService.getAll();
//        chbDepartments.setItems(departmentsList);
//        departmentService.save(new Department("Всі"));

//        System.out.println(ConnectionDBUtil.getCurrentIP());
//        ConnectionDBUtil.getCurrentIp();
//        clientService.testConnectionToDB();
    }

    public void tblViewHistoriesOnMouseClicked(){
        currentHistory = tblViewHistories.getSelectionModel().getSelectedItem();
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



    private void importFromExcel(){
        File file = new File("Контакти АБО Дитяча телешкола.xlsx");
        if (!file.exists()){
            FileChooser chooser = new FileChooser();
            file = chooser.showOpenDialog(mainStage);
        }
        clientService.clearTable();
        excelUtil.readExcel(file);
        currentDepartment = departmentService.getById(9);
        currentStatus = statusService.getById(1);
        fillClientData();
        fillChoiseBoxes();

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
}