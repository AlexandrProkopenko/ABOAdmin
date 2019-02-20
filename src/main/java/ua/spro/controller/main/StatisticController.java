package ua.spro.controller.main;
/**
 * Sample Skeleton for 'statisticscene.fxml' Controller Class
 */
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import ua.spro.ABOAdminApp;
import ua.spro.controller.MainController;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import ua.spro.entity.User;
import ua.spro.entity.save.StatisticSavedSettings;
import ua.spro.entity.statistic.ActionStatistic;
import ua.spro.model.statistic.StatisticModel;
import ua.spro.model.statistic.StatisticModelInterface;
import ua.spro.model.user.UserModelInterface;

public class StatisticController {

    private MainController mainController;
    private ObservableList<ActionStatistic> dataList;
    private StatisticModelInterface statisticModel;
    private UserModelInterface userModel;
    private ObservableList<User> users;
    private User currentUser;
    private User filterUser;
    private User allUsers;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private StatisticSavedSettings currentStatisticSavedSettings;

    @FXML private TableView<ActionStatistic> tblStatistic;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="chbUser"
    private ChoiceBox<User> chbUser; // Value injected by FXMLLoader

    @FXML // fx:id="dpFrom"
    private DatePicker dpFrom; // Value injected by FXMLLoader

    @FXML // fx:id="dpTo"
    private DatePicker dpTo; // Value injected by FXMLLoader

    @FXML // fx:id="clmnStatNumber"
    private TableColumn<ActionStatistic, Void> clmnStatNumber; // Value injected by FXMLLoader

    @FXML // fx:id="clmnStatDate"
    private TableColumn<ActionStatistic, LocalDateTime> clmnStatDate; // Value injected by FXMLLoader

    @FXML // fx:id="clmnStatClient"
    private TableColumn<ActionStatistic, String> clmnStatClient; // Value injected by FXMLLoader

    @FXML // fx:id="clmnStatComment"
    private TableColumn<ActionStatistic, String> clmnStatComment; // Value injected by FXMLLoader

    @FXML // fx:id="clmnStatAuthor"
    private TableColumn<ActionStatistic, String> clmnStatAuthor; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert chbUser != null : "fx:id=\"chbUser\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert dpFrom != null : "fx:id=\"dpFrom\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert dpTo != null : "fx:id=\"dpTo\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert clmnStatNumber != null : "fx:id=\"clmnStatNumber\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert clmnStatDate != null : "fx:id=\"clmnStatDate\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert clmnStatClient != null : "fx:id=\"clmnStatClient\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert clmnStatComment != null : "fx:id=\"clmnStatComment\" was not injected: check your FXML file 'statisticscene.fxml'.";
        assert clmnStatAuthor != null : "fx:id=\"clmnStatAuthor\" was not injected: check your FXML file 'statisticscene.fxml'.";

        dataSetup();
        tableSetup();
        choiseBoxSetup();
        loadSettings();
        ABOAdminApp.statisticController = this;
    }

    public void saveSettings(){
        currentStatisticSavedSettings.setDateFrom(dpFrom.getValue());
        currentStatisticSavedSettings.setDateTo(dpTo.getValue());
        currentStatisticSavedSettings.setUserId(chbUser.getValue().getUserId());
    }

    public void loadSettings(){
        if (currentUser != null){
            currentStatisticSavedSettings = currentUser.getSavedSettings().getStatisticSavedSettings();
        }else {
            currentStatisticSavedSettings = new StatisticSavedSettings();
        }
        System.out.println("Loading Statistic settings for current user\n");
        System.out.println(currentStatisticSavedSettings + "\n");

//        dateTo = currentStatisticSavedSettings.getDateTo();
        dateFrom = currentStatisticSavedSettings.getDateFrom();
        filterUser = users.get(currentStatisticSavedSettings.getUserId()-1);
        dpFrom.setValue(dateFrom);
        chbUser.setValue(filterUser);
        statisticModel.getData(filterUser, dateFrom, dateTo);
    }

    private void choiseBoxSetup(){
        chbUser.setItems(users);
        chbUser.setValue(allUsers);
        chbUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filterUser = chbUser.getValue();
                statisticModel.getData(filterUser, dateFrom, dateTo);
                saveSettings();
            }
        });

        dpFrom.setValue(dateFrom);
        dpFrom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dateFrom = dpFrom.getValue();
                if(filterUser == allUsers){
                    statisticModel.getData(dateFrom, dateTo);
                }else {
                    statisticModel.getData(filterUser, dateFrom, dateTo);
                }
                saveSettings();
            }
        });

        dpTo.setValue(dateTo);
        dpTo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dateTo = dpTo.getValue();
                if(filterUser == allUsers){
                    statisticModel.getData(dateFrom, dateTo);
                }else {
                    statisticModel.getData(filterUser, dateFrom, dateTo);
                }
                saveSettings();
            }
        });
    }

    private void dataSetup(){
        statisticModel = ABOAdminApp.getStatisticModel();
        userModel = ABOAdminApp.getUserModel();
        dataList = statisticModel.getDataList();

        users = userModel.getAllUsers();
        currentUser = userModel.getCurrentUser();
//        allUsers = new User(-1, "Всі", "");
//        currentUser = allUsers;
//        users.add(allUsers);
        for(User u: users){
            if(u.getLogin().equals("Всі")){
                filterUser = allUsers = u;
            }
        }

        dateTo = StatisticModel.MAX_DATE.toLocalDate();
        dateFrom = StatisticModel.MIN_DATE.toLocalDate();
    }

    private void tableSetup(){
        clmnStatNumber.setCellValueFactory(new PropertyValueFactory<ActionStatistic, Void>("№"));
        clmnStatNumber.setCellFactory(col -> new TableCell<ActionStatistic, Void>() {
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

        clmnStatDate.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        clmnStatDate.setCellFactory(col -> new TableCell<ActionStatistic, LocalDateTime>(){
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

        clmnStatClient.setCellValueFactory(new PropertyValueFactory<ActionStatistic, String>("clientDescription"));
        clmnStatComment.setCellValueFactory(new PropertyValueFactory<ActionStatistic, String>("comment"));
        clmnStatAuthor.setCellValueFactory(new PropertyValueFactory<ActionStatistic, String>("author"));
        tblStatistic.setItems(dataList);
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void importFromExcel(){
        statisticModel.reloadLists();
//        setCurrents();
//        setChoiseBoxesValues();
    }

    public void changeConnection(){
        statisticModel.reloadLists();
//        setCurrents();
//        setChoiseBoxesValues();
//        userModel.exit();
    }

    public void refresh(){
        if(filterUser == allUsers){
            statisticModel.getData(dateFrom, dateTo);
        }else {
            statisticModel.getData(filterUser, dateFrom, dateTo);
        }
    }
}
