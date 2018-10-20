/**
 * Sample Skeleton for 'newuserscene.fxml' Controller Class
 */

package ua.spro.controller.users;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ua.spro.controller.MainController;

public class NewUserSceneController {

    private MainController mainController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="fldPassword"
    private PasswordField fldPassword; // Value injected by FXMLLoader

    @FXML // fx:id="fldConfirmPassword"
    private PasswordField fldConfirmPassword; // Value injected by FXMLLoader

    @FXML // fx:id="fldLogin"
    private TextField fldLogin; // Value injected by FXMLLoader

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void btnCancelOnaction(ActionEvent event) {

    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert fldPassword != null : "fx:id=\"fldPassword\" was not injected: check your FXML file 'newuserscene.fxml'.";
        assert fldConfirmPassword != null : "fx:id=\"fldConfirmPassword\" was not injected: check your FXML file 'newuserscene.fxml'.";
        assert fldLogin != null : "fx:id=\"fldLogin\" was not injected: check your FXML file 'newuserscene.fxml'.";

    }
}
