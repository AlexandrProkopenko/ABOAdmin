package ua.spro.controller.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ua.spro.ABOAdminApp;
import ua.spro.controller.SettingsController;
import ua.spro.controller.main.AdminController;
import ua.spro.controller.main.StatisticController;
import ua.spro.entity.DBConnection;
import ua.spro.service.ClientService;
import ua.spro.service.jdbc.ClientServiceImpl;
import ua.spro.util.ConnectionDBUtil;

public class ConnectionSceneController {

    @FXML
    private TreeView<String> treeViewMenu;
    @FXML private ChoiceBox<DBConnection> chbConnections;
    @FXML private TextField fldConnectionName;
    @FXML private TextField fldHost;
    @FXML private TextField fldDataBase;
    @FXML private TextField fldURL;
    @FXML private PasswordField fldPassword;
    @FXML private TextField fldUser;
    @FXML private TextField fldPort;
    @FXML private Label labelConnectionStatus;
    @FXML private Label labelSaveStatus;
    private ObservableList<DBConnection> connections;
    private ObservableList<DBConnection> prevConnections;
    private DBConnection currentConnection;
    private DBConnection prevCurrentConnection;
    private DBConnection newConnection;

    private static final String urlBegin = "jdbc:mysql://";
    private static final String urlEnd = "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";

    private String host = "";
    private String dataBase = "";
    private String port = "";

    private String url;
    private String login;
    private String password;

    private ClientService clientService;

    private SettingsController settingsController;
    private AdminController adminController;
    private StatisticController statisticController;


    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public ConnectionSceneController() {

    }

    private void chbConnectionsOnAction(){
        hideLabels();
        if(currentConnection != null) {
            fldConnectionName.setText(currentConnection.getName());
            fldPort.setText(currentConnection.getPort());
            fldHost.setText(currentConnection.getHost());
            fldDataBase.setText(currentConnection.getDataBase());
            fldUser.setText(currentConnection.getUser());
            fldPassword.setText(currentConnection.getPassword());
            host = currentConnection.getHost();
            dataBase = currentConnection.getDataBase();
            port = currentConnection.getPort();
            buildUrl();
        }
    }



    private void fillData(){
        connections.addAll( ConnectionDBUtil.getInstance().getConnections() );
        prevConnections.addAll(connections);
        if(connections!=null) {
            chbConnections.setItems(connections);
            if(!connections.isEmpty()) {
                currentConnection = connections.get(0);
                prevCurrentConnection = currentConnection;
            }
            if(currentConnection != null) {
                chbConnections.setValue(currentConnection);
                chbConnectionsOnAction();
            }
        }
    }

    private void choiseBoxSetup(){
        connections = FXCollections.observableArrayList();
        prevConnections = FXCollections.observableArrayList();
        fillData();

        //дії чойз боксів при виборі елемента
        chbConnections.setOnAction(event -> {
            currentConnection = chbConnections.getValue();
            settingsController.getBtnApply().setDisable(false);
            chbConnectionsOnAction();
        });
    }

    private void hideLabels(){
        labelSaveStatus.setVisible(false);
        labelConnectionStatus.setVisible(false);
    }

    private void textFieldsSetup(){

        fldPort.textProperty().addListener((observable, oldValue, newValue) -> {
            port = fldPort.getText();
            buildUrl();
        });

        fldHost.textProperty().addListener((observable, oldValue, newValue) -> {
            host = fldHost.getText();
            buildUrl();
        });

        fldDataBase.textProperty().addListener((observable, oldValue, newValue) -> {
            dataBase = fldDataBase.getText();
            buildUrl();
        });
    }

    private void buildUrl(){
        String result = urlBegin + host + ":" + port + "/" + dataBase;
//        System.out.println("build url: " + result);
        fldURL.setText(result);
    }

    public void initialize(){
        hideLabels();
        choiseBoxSetup();
        textFieldsSetup();
        clientService = new ClientServiceImpl();
        adminController = ABOAdminApp.adminController;
        statisticController = ABOAdminApp.statisticController;
//        System.out.println("initialize connection properties");

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (currentConnection != null){
            boolean d = connections.remove(currentConnection);
            System.out.println(" remove from collection "+d);
            System.out.println(connections);
            System.out.println(currentConnection);
//            chbConnectionsOnAction();
        }
        currentConnection = connections.get(0);
        if (currentConnection != null){
            chbConnections.setValue(currentConnection);
            chbConnectionsOnAction();

        }

        if(ConnectionDBUtil.saveConnectionsToFile(connections, currentConnection) ){
            labelSaveStatus.setStyle("-fx-text-fill: green");
            labelSaveStatus.setText("Видалено");
        }else {
            labelSaveStatus.setStyle("-fx-text-fill: red");
            labelSaveStatus.setText("Не вдалося видалити");
        }

        labelSaveStatus.setVisible(true);
        settingsController.getBtnApply().setDisable(false);
    }



    @FXML
    void btnSaveOnAction(ActionEvent event) {

        newConnection = new DBConnection(
                fldConnectionName.getText(),
                fldHost.getText(),
                fldDataBase.getText(),
                fldPort.getText(),
                fldUser.getText(),
                fldPassword.getText(),
                fldURL.getText()
        );
        if(!connections.contains(newConnection)) {
            connections.add(newConnection);
            labelSaveStatus.setVisible(true);
            if (ConnectionDBUtil.saveConnectionsToFile(connections, newConnection)) {
                System.out.println("save connection");
                labelSaveStatus.setVisible(true);
                labelSaveStatus.setStyle("-fx-text-fill: green");
                labelSaveStatus.setText("Збережено");

            } else {
                labelSaveStatus.setVisible(true);
                labelSaveStatus.setStyle("-fx-text-fill: red");
                labelSaveStatus.setText("Не вдалося зберегти");
            }
            currentConnection = newConnection;
            chbConnections.setValue(currentConnection);
            labelSaveStatus.setVisible(true);
            settingsController.getBtnApply().setDisable(false);
        }

    }

    @FXML
    void btnTestConnectionOnAction(ActionEvent event) {
        System.out.println(currentConnection);
        login = fldUser.getText();
        password = fldPassword.getText();
        url = fldURL.getText()+urlEnd;

        System.out.println("url changed to " + ConnectionDBUtil.getInstance().getUrl());

        if(ConnectionDBUtil.getInstance().testConnectionToDB(url, login, password)){
            labelConnectionStatus.setVisible(true);
            labelConnectionStatus.setStyle("-fx-text-fill: green");
            labelConnectionStatus.setText("З'єднання встановлено");
        }else {
            labelConnectionStatus.setVisible(true);
            labelConnectionStatus.setStyle("-fx-text-fill: red");
            labelConnectionStatus.setText("Не вдалося під'єднатися до сервера");
        }
    }

    public void apply(){

        ConnectionDBUtil.getInstance().setLogin(currentConnection.getUser());
        ConnectionDBUtil.getInstance().setPassword(currentConnection.getPassword());
        ConnectionDBUtil.getInstance().setUrl(currentConnection.getFullURL());
        ConnectionDBUtil.saveConnectionsToFile(connections, currentConnection);
        System.out.println("url changed to " + ConnectionDBUtil.getInstance().getUrl());
        adminController.changeConnection();
        statisticController.changeConnection();
    }

    public void cancel(){
//        currentConnection = prevCurrentConnection;
//        connections.clear();
//        connections.addAll(prevConnections);
//        ConnectionDBUtil.saveConnectionsToFile(connections, currentConnection);
//        System.out.println(currentConnection);
//        ConnectionDBUtil.getInstance().setLogin(currentConnection.getUser());
//        ConnectionDBUtil.getInstance().setPassword(currentConnection.getPassword());
//        ConnectionDBUtil.getInstance().setUrl(currentConnection.getFullURL());
//        choiseBoxSetup();

        System.out.println("url changed to " + ConnectionDBUtil.getInstance().getUrl());

    }
}
