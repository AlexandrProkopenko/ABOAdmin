package ua.spro.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import ua.spro.entity.DBConnection;
import ua.spro.service.ClientService;
import ua.spro.service.impl.ClientServiceImpl;
import ua.spro.util.ConnectionDBUtil;

import java.io.*;

public class SettingsController {

    @FXML private TreeView<String> treeViewMenu;
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
    private DBConnection currentConnection;
    private DBConnection newConnection;
    private static final String fileName = "connections.ser";
    private static final String filePath = "sys/";
    private static final String urlBegin = "jdbc:mysql://";
    private static final String urlEnd = "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private File file;
    private String host = "";
    private String dataBase = "";
    private String port = "";
    private String url;
    private ClientServiceImpl clientService;

    private ObservableList<DBConnection> loadSavedConnectionsList(){
        ObservableList<DBConnection> list = FXCollections.observableArrayList();
        try{
            FileInputStream fis = new FileInputStream(file);
//            System.err.println("fis " + fis.available());
            if(fis.available() == 0) return list;
            ObjectInputStream ois = new ObjectInputStream(fis);
//            System.err.println("ois");
            boolean check=true;
            while (check) {
                try {
                    Object object = ois.readObject();
                    list.add((DBConnection) object);
                }catch(EOFException ex){
                    check = false;
                }
            }
        }

        catch (IOException e){
            System.err.println("IO exception");
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
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

    private boolean saveConnectionsToFile(ObservableList<DBConnection> list){
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(DBConnection connection: list) {
                oos.writeObject(connection);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void fileSetup(){
        file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        file = new File(filePath, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (IOException e){
            e.printStackTrace();

        }
    }

    private void choiseBoxSetup(){
        connections = loadSavedConnectionsList();
        if(connections!=null) {
            chbConnections.setItems(connections);
            if(!connections.isEmpty()) {
                currentConnection = connections.get(0);
            }
            if(currentConnection != null) {
                chbConnections.setValue(currentConnection);
                chbConnectionsOnAction();
            }
        }
        //дії чойз боксів при виборі елемента
        chbConnections.setOnAction(event -> {
            currentConnection = chbConnections.getValue();
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
        System.out.println("build url: " + result);
        fldURL.setText(result);
    }

    public void initialize(){
        hideLabels();
        fileSetup();
        choiseBoxSetup();
        textFieldsSetup();
        clientService = new ClientServiceImpl();

    }

    @FXML
    void btnApplyOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (currentConnection != null){
            connections.remove(currentConnection);
        }
        if( saveConnectionsToFile(connections) ){
            labelSaveStatus.setVisible(true);
            labelSaveStatus.setStyle("-fx-text-fill: green");
            labelSaveStatus.setText("Видалено");
        }else {
            labelSaveStatus.setVisible(true);
            labelSaveStatus.setStyle("-fx-text-fill: red");
            labelSaveStatus.setText("Не вдалося видалити");
        }
        currentConnection = connections.get(0);
        if (currentConnection != null){
            chbConnectionsOnAction();
        }
    }

    @FXML
    void btnOkOnAction(ActionEvent event) {

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

            if (saveConnectionsToFile(connections)) {
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
        }

    }

    @FXML
    void btnTestConnectionOnAction(ActionEvent event) {
        System.out.println(currentConnection);
        ConnectionDBUtil.setLogin(currentConnection.getUser());
        ConnectionDBUtil.setPassword(currentConnection.getPassword());
        ConnectionDBUtil.setUrl(currentConnection.getFullURL());

       if(clientService.testConnectionToDB()){
           labelConnectionStatus.setVisible(true);
           labelConnectionStatus.setStyle("-fx-text-fill: green");
           labelConnectionStatus.setText("З'єднання встановлено");
       }else {
           labelConnectionStatus.setVisible(true);
           labelConnectionStatus.setStyle("-fx-text-fill: red");
           labelConnectionStatus.setText("Не вдалося під'єднатися до сервера");
       }
    }

}
