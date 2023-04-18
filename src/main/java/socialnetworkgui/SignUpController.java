package socialnetworkgui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import socialnetwork.service.Service;
import socialnetworkgui.controller.MessageAlert;

import java.util.Objects;

public class SignUpController {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldConfirmPassword;

    private Service service;

    @FXML
    private void initialize() {
    }

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    public void onSaveButtonClick(){
        String name=textFieldName.getText();
        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        String confirmPassword=textFieldConfirmPassword.getText();
        if(service.findUserByUsername(username) != null){
            MessageAlert.showErrorMessage(null, "Acest username este deja utilizat");
        }
        else if(!Objects.equals(confirmPassword, password)){
            MessageAlert.showErrorMessage(null, "Parolele nu se potrivesc");
        }
        else{
            service.addUser(name,username,password);
        }
    }
}
