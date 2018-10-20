package ua.spro.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import ua.spro.ABOAdminApp;
import ua.spro.controller.main.AdminController;
import ua.spro.controller.users.InUserSceneController;
import ua.spro.controller.users.NewUserSceneController;
import ua.spro.controller.users.NoUserSceneController;


import java.io.IOException;

import static ua.spro.ABOAdminApp.mainStage;

public class MainController {

    private AdminController adminController;

    //    subScene for changing main action zone
    @FXML private SubScene subScene;

    private FXMLLoader adminLoader;
    private static final String adminFXMLPath = "/ua/spro/fxml/mainsubscenes/adminscene.fxml";
    private Parent adminRoot;
    
//    User`s subscene parameters
    private FXMLLoader inUserLoader;
    private FXMLLoader noUserLoader;
    private FXMLLoader newUserLoader;
    private static final String inUserFXMLPath = "/ua/spro/fxml/userscenes/inuserscene.fxml";
    private static final String noUserFXMLPath = "/ua/spro/fxml/userscenes/nouserscene.fxml";
    private static final String newUserFXMLPath = "/ua/spro/fxml/userscenes/newuserscene.fxml";
    private Parent inUserRoot;
    private Parent noUserRoot;
    private Parent newUserRoot;
    private InUserSceneController inUserController;
    private NoUserSceneController noUserController;
    private NewUserSceneController newUserController;
    

    //  subscene for changing user authorization
    @FXML private SubScene subSceneUser;
    
    @FXML private AnchorPane mainAnchorPane;
    @FXML private ToggleButton btnAdminScene;
    @FXML private ToggleButton btnVisitScene;


    private void loadScenes(){
        adminLoader = new FXMLLoader();
        inUserLoader = new FXMLLoader();
        noUserLoader = new FXMLLoader();
        newUserLoader = new FXMLLoader();
        try {
            adminLoader.setLocation(getClass().getResource(adminFXMLPath));
            adminRoot = adminLoader.load();
            adminController = adminLoader.getController();
            adminController.setMainController(this);

            inUserLoader.setLocation(getClass().getResource(inUserFXMLPath));
            inUserRoot = inUserLoader.load();
            inUserController = inUserLoader.getController();
            inUserController.setMainController(this);

            noUserLoader.setLocation(getClass().getResource(noUserFXMLPath));
            noUserRoot = noUserLoader.load();
            noUserController = noUserLoader.getController();
            noUserController.setMainController(this);

            newUserLoader.setLocation(getClass().getResource(newUserFXMLPath));
            newUserRoot = newUserLoader.load();
            newUserController = newUserLoader.getController();
            newUserController.setMainController(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize(){
        loadScenes();
        panelSetup();
    }

    private void panelSetup(){
        //        зміна розмірів сабсцени відповідно до розмірів батьківської панелі АнкорПейн
        DoubleProperty height = new SimpleDoubleProperty(mainAnchorPane.heightProperty().getValue()-100);
        mainAnchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            subScene.setHeight(mainAnchorPane.getHeight()- (subScene.getLayoutY()));
        });
        subScene.widthProperty().bind(mainAnchorPane.widthProperty());
        btnAdminScene.setSelected(true);
        btnAdminOnAction();
    }

    public void miSettingsOnAction(){
        System.out.println("settings");
        ABOAdminApp.settingsStage.showAndWait();
    }

    public void miImportExcelOnAction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Імпорт з excel файлу");
        alert.initOwner(mainStage);
        alert.setHeaderText("Ця процедура видалить всі існуючі дані і заповнить базу даних новими!");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
//            importFromExcel();
        }else {

        }
    }

    public void miCloseOnAction(){

        mainStage.close();
    }

    public void btnAdminOnAction(){
        subScene.setRoot(adminRoot);
    }
}
