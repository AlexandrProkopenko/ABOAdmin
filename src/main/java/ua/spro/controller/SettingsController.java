package ua.spro.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;

public class SettingsController {

    @FXML private SubScene subScene;
    @FXML private TreeView<String> treeViewMenu;

    private TreeItem<String> connectionMenuItem;
    private TreeItem<String> newMenuItem;
    private TreeItem<String> rootMenuItem;
    private static final String connectionFXMLPath = "/ua/spro/fxml/connectionscene.fxml";
    private static final String fooFXMLPath = "/ua/spro/fxml/foo.fxml";
    private Parent connectionRoot;
    private Parent fooRoot;



    public void initialize(){

        try {
            connectionRoot = FXMLLoader.load(getClass().getResource(connectionFXMLPath));
            fooRoot = FXMLLoader.load(getClass().getResource(fooFXMLPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        treeViewMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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



    @FXML
    void btnApplyOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnOkOnAction(ActionEvent event) {

    }

}
