/**
 * Sample Skeleton for 'nouserscene.fxml' Controller Class
 */

package ua.spro.controller.users;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import ua.spro.controller.MainController;

public class NoUserSceneController {

    private MainController mainController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="chbLogin"
    private ChoiceBox<?> chbLogin; // Value injected by FXMLLoader

    @FXML // fx:id="fldPassword"
    private PasswordField fldPassword; // Value injected by FXMLLoader

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void btnCreateUserOnaction(ActionEvent event) {

    }

    @FXML
    void btnEnterOnAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert chbLogin != null : "fx:id=\"chbLogin\" was not injected: check your FXML file 'nouserscene.fxml'.";
        assert fldPassword != null : "fx:id=\"fldPassword\" was not injected: check your FXML file 'nouserscene.fxml'.";

    }
}
