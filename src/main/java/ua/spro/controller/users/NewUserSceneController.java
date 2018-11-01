/**
 * Sample Skeleton for 'newuserscene.fxml' Controller Class
 */

package ua.spro.controller.users;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ua.spro.controller.MainController;
import ua.spro.entity.SubUser;
import ua.spro.model.user.UserModelInterface;
import ua.spro.model.user.UserState;

public class NewUserSceneController implements Observer {

    private MainController mainController;
    private UserModelInterface userModel;

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
    @FXML private Label lbMessage;


    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public UserModelInterface getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModelInterface userModel) {
        this.userModel = userModel;
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        switch (userModel.getUserState()){
            case CREATING_NEW:
                userModel.changeState(UserState.NOT_ENTERED);
                break;
            case EDITING:
                userModel.changeState(UserState.ENTERED);
                break;
        }
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {
        switch (userModel.getUserState()){
            case CREATING_NEW:
                if( !userModel.createUser(
                        new SubUser(
                        fldLogin.getText(),
                        fldPassword.getText(),
                        fldConfirmPassword.getText()
                )) ){
                    lbMessage.setText("Помилка");
                    lbMessage.setStyle("-fx-text-fill: red;");
                }{

            }
                break;
            case EDITING:
                if(
                !userModel.update(new SubUser(
                        fldLogin.getText(),
                        fldPassword.getText(),
                        fldConfirmPassword.getText()
                ))){
                    lbMessage.setText("Помилка");
                    lbMessage.setStyle("-fx-text-fill: red;");
                }
                break;
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert fldPassword != null : "fx:id=\"fldPassword\" was not injected: check your FXML file 'newuserscene.fxml'.";
        assert fldConfirmPassword != null : "fx:id=\"fldConfirmPassword\" was not injected: check your FXML file 'newuserscene.fxml'.";
        assert fldLogin != null : "fx:id=\"fldLogin\" was not injected: check your FXML file 'newuserscene.fxml'.";

    }

    @Override
    public void update(Observable o, Object arg) {

        lbMessage.setStyle("-fx-text-fill: black;");
        if(userModel.getUserState() == UserState.EDITING){
            fldLogin.setText(userModel.getCurrentUser().getLogin());
            fldPassword.setText(userModel.getCurrentUser().getPassword());
            fldConfirmPassword.setText(userModel.getCurrentUser().getPassword());

            lbMessage.setText("Зберегти");
        }else if(userModel.getUserState() == UserState.CREATING_NEW) {
            fldLogin.setText("");
            fldPassword.setText("");
            fldConfirmPassword.setText("");
            lbMessage.setText("Створити");
        }
    }
}
