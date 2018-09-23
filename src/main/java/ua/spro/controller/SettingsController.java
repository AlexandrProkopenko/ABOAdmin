package ua.spro.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

public class SettingsController {

    @FXML
    private TreeView<?> treeViewMenu;

    @FXML
    private ChoiceBox<?> chbConnections;

    @FXML
    private TextField fldConnectionName;

    @FXML
    private TextField fldHost;

    @FXML
    private TextField fldDataBase;

    @FXML
    private TextField fldURL;

    @FXML
    private PasswordField fldPassword;

    @FXML
    private TextField fldUser;

    @FXML
    private TextField fldPort;

    @FXML
    private Label labelConnectionStatus;

    @FXML
    void btnApplyOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnOkOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnTestConnectionOnAction(ActionEvent event) {

    }

}
