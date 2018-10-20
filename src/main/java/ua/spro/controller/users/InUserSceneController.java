/**
 * Sample Skeleton for 'inuserscene.fxml' Controller Class
 */

package ua.spro.controller.users;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import ua.spro.controller.MainController;

public class InUserSceneController {

    private MainController mainController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lbUserName"
    private Label lbUserName; // Value injected by FXMLLoader

    @FXML // fx:id="chbUsers"
    private ChoiceBox<?> chbUsers; // Value injected by FXMLLoader

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void btnExitUserOnAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lbUserName != null : "fx:id=\"lbUserName\" was not injected: check your FXML file 'inuserscene.fxml'.";
        assert chbUsers != null : "fx:id=\"chbUsers\" was not injected: check your FXML file 'inuserscene.fxml'.";

    }
}