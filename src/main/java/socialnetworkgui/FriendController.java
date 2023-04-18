package socialnetworkgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Friend;
import socialnetwork.service.Service;
import socialnetworkgui.controller.MessageAlert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;


public class FriendController {
    Service service;
    ObservableList<Friend> model = FXCollections.observableArrayList();

    Integer currentUserId;

    Boolean requests=false;


    @FXML
    TableView<Friend> tableView;

    @FXML
    TableColumn<Friend, Integer> tableColumnId1;

    @FXML
    TableColumn<Friend, Integer> tableColumnId2;

    @FXML
    TableColumn<Friend, LocalDateTime> tableColumnFriendsFrom;

    @FXML
    TableColumn<Friend, Boolean> tableColumnPending;

    @FXML
    TableColumn<Friend, Boolean> tableColumnAccepted;

    public void setService(Service service, Integer currentUserId, Boolean requests){
        this.service = service;
        this.currentUserId = currentUserId;
        this.requests = requests;
        initModel();
    }

    private void initModel(){
        if(requests){
            Iterable<Friend> friends = service.findAllRequests(currentUserId);
            List<Friend> friendList = StreamSupport.stream(friends.spliterator(), false).toList();
            model.setAll(friendList);
        }
        else {
            Iterable<Friend> friends = service.findAllFriendsOfUser(currentUserId);
            List<Friend> friendList = StreamSupport.stream(friends.spliterator(), false).toList();
            model.setAll(friendList);
        }
    }

    @FXML
    public void initialize() {
        tableColumnId1.setCellValueFactory(new PropertyValueFactory<Friend, Integer>("id1"));
        tableColumnId2.setCellValueFactory(new PropertyValueFactory<Friend, Integer>("id2"));
        tableColumnFriendsFrom.setCellValueFactory(new PropertyValueFactory<Friend, LocalDateTime>("friendsFrom"));
        tableColumnPending.setCellValueFactory(new PropertyValueFactory<Friend, Boolean>("pending"));
        tableColumnAccepted.setCellValueFactory(new PropertyValueFactory<Friend, Boolean>("accepted"));
        tableView.setItems(model);
    }

    public void handleAccept(){
        Friend selected = (Friend) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.handleFriendRequest(selected.getId(), true);
        }
        else MessageAlert.showErrorMessage(null, "Nu ati selectat nici o cerere");
    }

    public void handleDeny(){
        Friend selected = (Friend) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteFriend(selected.getId());
        }
        else MessageAlert.showErrorMessage(null, "Nu ati selectat nici o cerere");
    }

}
