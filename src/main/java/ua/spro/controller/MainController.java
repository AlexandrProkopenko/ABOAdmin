package ua.spro.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ua.spro.ABOAdminApp;
import ua.spro.controller.main.AdminController;
import ua.spro.controller.main.StatisticController;
import ua.spro.controller.users.InUserSceneController;
import ua.spro.controller.users.NewUserSceneController;
import ua.spro.controller.users.NoUserSceneController;
import ua.spro.entity.save.SavedSettings;
import ua.spro.entity.save.Screen;
import ua.spro.model.admin.AdminModel;
import ua.spro.model.admin.AdminModelInterface;
import ua.spro.model.user.UserModel;
import ua.spro.model.user.UserModelInterface;
import ua.spro.model.user.UserState;
import ua.spro.util.ConnectionDBUtil;
import ua.spro.util.ReadExcelUtil;


import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static ua.spro.ABOAdminApp.mainStage;

public class MainController implements Observer {

    private AdminController adminController;

    //    subScene for changing main action zone
    @FXML private SubScene subScene;
//  admin controller
    private FXMLLoader adminLoader;
    private static final String adminFXMLPath = "/ua/spro/fxml/mainsubscenes/adminscene.fxml";
    private Parent adminRoot;
// statistic controller
    private FXMLLoader statisticLoader;
    private static final String statisticFXMLPath = "/ua/spro/fxml/mainsubscenes/statisticscene.fxml";
    private Parent statisticRoot;
    private StatisticController statisticController;
    
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

    private UserModel userModel;
    private AdminModelInterface adminModel;
    private ReadExcelUtil excelUtil;

    private SavedSettings currentSavedSettings;
    private Screen currentScreen;
    

    //  subscene for changing user authorization
    @FXML private SubScene subSceneUser;
    
    @FXML private AnchorPane mainAnchorPane;
    @FXML private ToggleButton btnAdminScene;
    @FXML private ToggleButton btnVisitScene;
    @FXML private ToggleButton btnStatisticScene;
    @FXML private MenuItem miImportExcel;


    public AdminController getAdminController() {
        return adminController;
    }

    public void initialize(){
        modelsSetup();
        loadScenes();
        panelSetup();
        userModelSetup();
        excelUtil = new ReadExcelUtil( userModel);
//Версія для колег
//        miImportExcel.setDisable(true);
        loadSettings();
    }

    public void saveSettings(){
       currentSavedSettings.setScreen(currentScreen);
    }

    public void loadSettings(){
        if (userModel.getCurrentUser() != null){
            currentSavedSettings = userModel.getCurrentUser().getSavedSettings();
        }else {
            currentSavedSettings = new SavedSettings();
        }
        currentScreen = currentSavedSettings.getScreen();
        switch (currentScreen){
            case ADMIN_SCENE:
                btnAdminOnAction();
                btnAdminScene.setSelected(true);
                break;
            case STATISTIC_SCENE:
                btnSttstcOnActon();
                btnStatisticScene.setSelected(true);
                break;
            case VISIT_SCENE:
                btnStatisticScene.setSelected(true);
                break;
        }
    }

    private void modelsSetup(){
        adminModel = ABOAdminApp.getAdminModel();
        userModel = ABOAdminApp.getUserModel();
    }

    private void loadScenes(){
        adminLoader = new FXMLLoader();
        statisticLoader = new FXMLLoader();
        inUserLoader = new FXMLLoader();
        noUserLoader = new FXMLLoader();
        newUserLoader = new FXMLLoader();
        try {
            adminLoader.setLocation(getClass().getResource(adminFXMLPath));
            adminRoot = adminLoader.load();
            adminController = adminLoader.getController();
            adminController.setMainController(this);

            statisticLoader.setLocation(getClass().getResource(statisticFXMLPath));
            statisticRoot = statisticLoader.load();
            statisticController = statisticLoader.getController();
            statisticController.setMainController(this);

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
        subSceneUser.setVisible(true);
    }

    private void userModelSetup(){
        userModel.addObserver(this);

        inUserController.setUserModel(userModel);
        noUserController.setUserModel(userModel);
        newUserController.setUserModel(userModel);

        userModel.addObserver(inUserController);
        userModel.addObserver(newUserController);
        userModel.addObserver(noUserController);
        userModel.changeState();


        noUserController.lateInitialization();
//        adminController.lateInitialization(userModel);

        System.out.println("MainController setingUserModelToAdminController. User model: " + userModel);
//        adminController.setUserModel(userModel);
//        adminController.lateInitialization(userModel, adminModel);
    }



    private void panelSetup(){
        //        зміна розмірів сабсцени відповідно до розмірів батьківської панелі АнкорПейн
        DoubleProperty height = new SimpleDoubleProperty(mainAnchorPane.heightProperty().getValue()-100);
        mainAnchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            subScene.setHeight(mainAnchorPane.getHeight()- (subScene.getLayoutY()));
        });
        subScene.widthProperty().bind(mainAnchorPane.widthProperty());
//        btnAdminScene.setSelected(true);
//        btnAdminOnAction();
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
            importFromExcel(mainStage);
            adminController.importFromExcel();
            statisticController.importFromExcel();
        }else {

        }
    }


    public boolean importFromExcel(Stage owner) {

        File file = new File("Контакти АБО Дитяча телешкола.xlsx");
        if (!file.exists()){
            FileChooser chooser = new FileChooser();
            file = chooser.showOpenDialog(owner);
        }

        excelUtil.readExcel(file);

        return false;
    }

    public void miCloseOnAction(){

        mainStage.close();
    }

    public void btnAdminOnAction(){
        currentScreen = Screen.ADMIN_SCENE;
        saveSettings();
        subScene.setRoot(adminRoot);
    }

    public void btnSttstcOnActon(){
        currentScreen = Screen.STATISTIC_SCENE;
        saveSettings();
        statisticController.refresh();
        subScene.setRoot(statisticRoot);

    }

    public void btnVisitSceneOnAction(){
        currentScreen = Screen.VISIT_SCENE;
        saveSettings();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Main controller update");
        if(o instanceof UserModel){

            if(userModel.getUserState() == UserState.NOT_ENTERED){
                subSceneUser.setRoot(noUserRoot);
                subScene.setDisable(true);
                miImportExcel.setDisable(true);
                if(!ConnectionDBUtil.getInstance().isConnected()) {
                    subSceneUser.setDisable(true);
                }else {
                    subSceneUser.setDisable(false);
                }

            }else if(userModel.getUserState() == UserState.ENTERED){
                subSceneUser.setRoot(inUserRoot);
                subScene.setDisable(false);
//                miImportExcel.setDisable(false);
            }else if(userModel.getUserState() == UserState.CREATING_NEW){
                subSceneUser.setRoot(newUserRoot);
//                newUserController

            }else if(userModel.getUserState() == UserState.EDITING){
                subSceneUser.setRoot(newUserRoot);
            }

        }
//        if(adminController.getUserModel() == null){
//            adminController.lateInitialization();
//        }
    }
}
