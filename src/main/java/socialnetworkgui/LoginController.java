package socialnetworkgui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.domain.User;
import socialnetwork.service.Service;
import socialnetworkgui.controller.MessageAlert;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;

    private Service service;

    @FXML
    private void initialize() {
    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void onLogInButtonClick(){
        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        if(service.findUserByUsername(username) == null){
            MessageAlert.showErrorMessage(null, "Username incorect");
        }
        else if(!Objects.equals(service.findUserByUsername(username).getPasssword(), password)){
            MessageAlert.showErrorMessage(null, "Parola incorecta");
        }
        else{
            User currentUser = service.findUserByUsername(username);
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("views/account-view.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Welcome " + currentUser.getUserName() + "!");
                AccountController accountController = loader.getController();
                accountController.setService(service, currentUser);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onSignUpButtonClick(){

            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("views/signup-view.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                SignUpController signUpController = loader.getController();
                signUpController.setService(service);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
}
