package socialnetworkgui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.domain.Friend;
import socialnetwork.domain.User;
import socialnetwork.service.Service;
import socialnetwork.tools.Tuple;
import socialnetwork.validation.exceptions.ValidationException;
import socialnetworkgui.FriendController;
import socialnetworkgui.controller.MessageAlert;

import java.io.IOException;

public class AccountController {
    private Service service;

    private User currentUser;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private void initialize() {
    }

    public void setService(Service service, User currentUser){
        this.service = service;
        this.currentUser = currentUser;
    }

    @FXML
    public void onAddFriendButtonClick(){
        String username=textFieldUsername.getText();
        if(service.findUserByUsername(username) == null){
            MessageAlert.showErrorMessage(null, "Nu exista user cu acest username");
        }
        else {
            try {
                service.addFriend(currentUser, service.findUserByUsername(username));
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Add","Cererea a fost trimisa");
            }
            catch (ValidationException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
    }

    @FXML
    public void onRemoveFriendButtonClick(){
        String username=textFieldUsername.getText();
        if(service.findUserByUsername(username) == null){
            MessageAlert.showErrorMessage(null, "Nu exista user cu acest username");
        }
        else{
            Integer id1 = currentUser.getId();
            Integer id2 = service.findUserByUsername(username).getId();
            if(service.deleteFriend(new Tuple<>(id1,id2)) != null){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Delete","Prietenul a fost sters");
            }
            else{
                MessageAlert.showErrorMessage(null,"Nu s-a putut efectua stergerea");
            }
        }
    }

    @FXML
    public void onShowFriendsButtonClick(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/friend-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            FriendController friendController = loader.getController();
            friendController.setService(service, currentUser.getId(), false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onFriendRequestsButtonClick(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/friendRequests-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            FriendController friendController = loader.getController();
            friendController.setService(service, currentUser.getId(), true);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
