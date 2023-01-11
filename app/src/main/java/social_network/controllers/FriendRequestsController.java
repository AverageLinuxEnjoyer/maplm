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
import java.sql.SQLOutput;
import java.util.ArrayList;

public class FriendRequestsController implements Controller {
    private Service service;

    @FXML
    private TextField search;

    @FXML
    private TableView<FriendView> sentRequests;
    @FXML
    private TableView<FriendView> receivedRequests;

    @FXML
    private Button acceptRequestButton;

    @FXML
    private Button declineRequestButton;

    @FXML
    private Button deleteRequestButton;

    @FXML
    private void updateButtons() {
        this.updateAcceptRequestButton();
        this.updateDeclineRequestButton();
        this.updateDeleteRequestButton();
    }

    @FXML
    private void updateAcceptRequestButton() {
        FriendView selected = receivedRequests.getSelectionModel().getSelectedItem();

        if (selected == null) {
            acceptRequestButton.setDisable(true);
        } else {
            acceptRequestButton.setDisable(false);
        }
    }

    @FXML
    private void updateDeclineRequestButton() {
        FriendView selected = receivedRequests.getSelectionModel().getSelectedItem();

        if (selected == null) {
            declineRequestButton.setDisable(true);
        } else {
            declineRequestButton.setDisable(false);
        }
    }

    @FXML
    private void updateDeleteRequestButton() {
        FriendView selected = sentRequests.getSelectionModel().getSelectedItem();

        if (selected == null) {
            deleteRequestButton.setDisable(true);
        } else {
            deleteRequestButton.setDisable(false);
        }
    }


    @FXML
    private void acceptRequest() {
        FriendView selected = receivedRequests.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        service.acceptFriendRequest(selected.getId(), service.getLoggedInUserId());
        updateTables();
    }

    @FXML
    private void declineRequest() {
        FriendView selected = receivedRequests.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        service.deleteFriendship(selected.getId(), service.getLoggedInUserId());
        updateTables();
    }

    @FXML
    private void deleteRequest() {
        FriendView selected = sentRequests.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        service.deleteFriendship(selected.getId(), service.getLoggedInUserId());
        updateTables();
    }

    @FXML
    private void updateTables() {
        ArrayList<FriendView> sent = new ArrayList<>();
        ArrayList<FriendView> received = new ArrayList<>();

        for (User u: service.getAllUsers()) {
            if (!u.getLastName().toLowerCase().contains(search.getText().toLowerCase()) &&
                !u.getFirstName().toLowerCase().contains(search.getText().toLowerCase())) {
                continue;
            }

            Friendship f = service.getFriendshipBetween(u.getId(), service.getLoggedInUserId());
            if (f == null) continue;
            if (!f.getStatus().equals("pending")) continue;

            if (f.getId1().equals(u.getId())) {
                received.add(new FriendView(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), f.getFriendsFrom()));
            } else {
                sent.add(new FriendView(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), f.getFriendsFrom()));
            }
        }

        sentRequests.getItems().setAll(sent);
        receivedRequests.getItems().setAll(received);

        updateButtons();
    }

    @FXML
    private void initialize() {
        TableColumn<FriendView, String> first_name1 = new TableColumn<>("First name");
        first_name1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<FriendView, String> last_name1 = new TableColumn<>("Last name");
        last_name1.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<FriendView, String> sentAt = new TableColumn<>("Sent at");
        sentAt.setCellValueFactory(new PropertyValueFactory<>("friendsFrom"));
        sentAt.setMinWidth(100);

        sentRequests.getColumns().addAll(first_name1, last_name1, sentAt);

        TableColumn<FriendView, String> first_name2 = new TableColumn<>("First name");
        first_name2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<FriendView, String> last_name2 = new TableColumn<>("Last name");
        last_name2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<FriendView, String> receivedAt = new TableColumn<>("Received at");
        receivedAt.setCellValueFactory(new PropertyValueFactory<>("friendsFrom"));
        receivedAt.setMinWidth(100);

        receivedRequests.getColumns().addAll(first_name2, last_name2, receivedAt);
    }
    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void update() {
        Controller.super.update();
        this.updateTables();
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("main_menu");
    }

}
