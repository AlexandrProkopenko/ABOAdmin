/**
 * Sample Skeleton for 'nouserscene.fxml' Controller Class
 */

package ua.spro.controller.users;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import ua.spro.controller.MainController;
import ua.spro.entity.User;
import ua.spro.model.user.UserModelInterface;
import ua.spro.model.user.UserState;

public class NoUserSceneController implements Observer {

    private MainController mainController;
    private UserModelInterface userModel;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="chbLogin"
    private ChoiceBox<User> chbLogin; // Value injected by FXMLLoader

    @FXML // fx:id="fldPassword"
    private PasswordField fldPassword; // Value injected by FXMLLoader
    @FXML private Label lbWrongPass;

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
    void btnCreateUserOnAction(ActionEvent event) {
       userModel.changeState(UserState.CREATING_NEW);
    }

    @FXML
    void btnEnterOnAction(ActionEvent event) {
       if( !userModel.checkAccess(new User(chbLogin.getValue().getLogin(), fldPassword.getText()))){

           lbWrongPass.setVisible(true);
        }else {
           System.out.println("Acces granted, fields are empty");
           fldPassword.setText("");
           lbWrongPass.setVisible(false);
       }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert chbLogin != null : "fx:id=\"chbLogin\" was not injected: check your FXML file 'nouserscene.fxml'.";
        assert fldPassword != null : "fx:id=\"fldPassword\" was not injected: check your FXML file 'nouserscene.fxml'.";

//
        System.out.println("initialize NoUserController");
    }

    public void lateInitialization(){
        System.out.println("noUserController LateInitialization");
        choiseBoxSetup();
        lbWrongPass.setVisible(false);

    }

    private void choiseBoxSetup(){
        User capture = new User("Виберіть користувача", "");
        chbLogin.setValue(capture);

        if(userModel.getAllUsers()!=null) {
            chbLogin.setItems(userModel.getAllUsers());
        }
        //дії чойз боксів при виборі елемента
        chbLogin.setOnAction(event -> {
            lbWrongPass.setVisible(false);
        });


        fldPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lbWrongPass.setVisible(false);
            }
        });
    }

    public NoUserSceneController() {
        System.out.println("constructor NoUserController");
    }

    @Override
    public void update(Observable o, Object arg) {
        chbLogin.setItems(userModel.getAllUsers());
    }
}
