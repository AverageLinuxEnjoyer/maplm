package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import social_network.App;
import social_network.controllers.views.FriendView;
import social_network.controllers.views.UserRelationshipView;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.service.Service;

import java.io.IOException;
import java.util.ArrayList;

public class FriendsController implements Controller {
    private Service service;

    @FXML
    TableView<FriendView> table;

    @FXML
    TextField search;

    @FXML
    Button removeFriendButton;

    @FXML
    private void removeFriend() {
        FriendView selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        service.deleteFriendship(service.getLoggedInUserId(), selected.getId());
        this.updateTable();
    }

    @FXML
    private void updateRemoveFriendButton() {
        FriendView selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            removeFriendButton.setDisable(true);
            return;
        } else {
            removeFriendButton.setDisable(false);
        }
    }

    @FXML
    private void updateTable() {
        ArrayList<FriendView> res = new ArrayList<>();
        for (User u : service.getAllUsers()) {
            Friendship f = service.getFriendshipBetween(u.getId(), service.getLoggedInUserId());
            if (f == null) continue;
            if (!f.getStatus().equals("accepted")) continue;

            res.add(new FriendView(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), f.getFriendsFrom()));
        }

        table.getItems().setAll(res);
        this.updateRemoveFriendButton();
    }

    @FXML
    private void initialize() {
        TableColumn<FriendView, String> first_name = new TableColumn<>("First name");
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<FriendView, String> last_name = new TableColumn<>("Last name");
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<FriendView, String> friendsFrom = new TableColumn<>("Friends from");
        friendsFrom.setCellValueFactory(new PropertyValueFactory<>("friendsFrom"));
        friendsFrom.setMinWidth(100);

        table.getColumns().addAll(first_name, last_name, friendsFrom);
    }

    @Override
    public void update() {
        Controller.super.update();
        this.updateTable();
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("main_menu");
    }

}
