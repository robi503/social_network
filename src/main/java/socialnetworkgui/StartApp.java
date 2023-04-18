package socialnetworkgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.service.Service;
import socialnetwork.validation.FriendValidator;
import socialnetwork.validation.UserValidator;

public class StartApp extends Application {
    Service service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FriendValidator friendValidator = new FriendValidator();
        UserValidator userValidator = new UserValidator();
        this.service = new Service(friendValidator,userValidator);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/login-view.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        LoginController loginController = loader.getController();
        loginController.setService(service);

        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
