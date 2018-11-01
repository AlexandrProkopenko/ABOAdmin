package ua.spro.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ua.spro.ABOAdminApp;
import ua.spro.controller.main.AdminController;
import ua.spro.controller.settings.ConnectionSceneController;

import java.io.IOException;

public class SettingsController {

    @FXML private SubScene subScene;
    @FXML private TreeView<String> treeViewMenu;
    @FXML private Button btnApply;

    private TreeItem<String> connectionMenuItem;
    private TreeItem<String> newMenuItem;
    private TreeItem<String> rootMenuItem;
    private static final String connectionFXMLPath = "/ua/spro/fxml/settings/connectionscene.fxml";
    private static final String fooFXMLPath = "/ua/spro/fxml/settings/foo.fxml";
    private Parent connectionRoot;
    private Parent fooRoot;
    private FXMLLoader connectionLoader;
    private ConnectionSceneController connectionSceneController;

    private TreeItem<String> selectedItem;



    public Button getBtnApply() {
        return btnApply;
    }

    private void treeViewSetup(){
        connectionMenuItem = new TreeItem<>();
        rootMenuItem = new TreeItem<>();
        rootMenuItem.setExpanded(true);
        treeViewMenu.setShowRoot(false);
        treeViewMenu.setRoot(rootMenuItem);

        connectionMenuItem = new TreeItem<>("З'єднання");
        newMenuItem = new TreeItem<>("foo");
        rootMenuItem.getChildren().add(connectionMenuItem);
        rootMenuItem.getChildren().add(newMenuItem);
        subScene.setRoot(connectionRoot);
        selectedItem = connectionMenuItem;

        treeViewMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem = newValue;

            switch (newValue.getValue()){
                case "З'єднання":

                    subScene.setRoot(connectionRoot);
                    break;
                case "foo":
                    subScene.setRoot(fooRoot);
                    break;
                default:
                    break;
            }
        });
    }

    private void subSceneSetup(){
        connectionLoader = new FXMLLoader();
        try {
            connectionLoader.setLocation(getClass().getResource(connectionFXMLPath));
            connectionRoot = connectionLoader.load();
            connectionSceneController = connectionLoader.getController();
            connectionSceneController.setSettingsController(this);


            fooRoot = FXMLLoader.load(getClass().getResource(fooFXMLPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        subSceneSetup();
        treeViewSetup();

    }



    @FXML
    void btnApplyOnAction() {
        switch (selectedItem .getValue()){
            case "З'єднання":
                connectionSceneController.apply();
                break;
            case "foo":

                break;
            default:
                break;
        }

        btnApply.setDisable(true);
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        switch (selectedItem .getValue()){
            case "З'єднання":
//                connectionSceneController.cancel();
                btnApply.setDisable(true);
                break;
            case "foo":

                break;
            default:
                break;
        }

        ABOAdminApp.settingsStage.close();
    }

    @FXML
    void btnOkOnAction(ActionEvent event) {
        if(!btnApply.isDisable()) {
            btnApplyOnAction();
            System.out.println("applied");
        }
        ABOAdminApp.settingsStage.close();
    }


}
