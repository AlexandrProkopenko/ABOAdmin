package ua.spro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.spro.controller.MainController;

public class ABOAdminApp extends Application {

    private Stage mainStage;
    private MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("ABO Admin");
        mainStage.setResizable(false);
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("/ua/spro/fxml/mainform.fxml"));
        Parent root = mainLoader.load();

        mainStage.setScene(new Scene(root));
        mainController = mainLoader.getController();
        mainController.setMainStage(mainStage);

        Image ico = new Image("ua/spro/images/logo.jpg");
        mainStage.getIcons().add(ico);

//        mainStage.getScene().getStylesheets().add("ua/spro/css/caspian.css");
        mainStage.show();
    }
}
