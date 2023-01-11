package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import social_network.App;
import social_network.controllers.views.UserRelationshipView;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.service.Service;

import java.io.IOException;
import java.util.ArrayList;

public class UsersController implements Controller {
    private Service service =  null;

    @FXML
    TableView<UserRelationshipView> table;

    @FXML
    TextField search;

    @FXML
    Button sendRequestButton;

    @FXML
    Button messagesButton;

    @Override
    public void update() {
        Controller.super.update();
        this.updateTable();
    }

    @FXML
    private void updateButtons()
    {
        this.updateMessagesButton();
        this.updateSendRequestButton();
    }

    @FXML
    private void sendRequest() {
        UserRelationshipView selected = table.getSelectionModel().getSelectedItem();

        service.sendFriendRequest(service.getLoggedInUserId(), selected.getId());
        this.updateTable();
    }

    private void updateMessagesButton() {
        UserRelationshipView selected = table.getSelectionModel().getSelectedItem();

        messagesButton.setDisable(selected == null);
    }

    private void updateSendRequestButton() {
        UserRelationshipView selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            sendRequestButton.setDisable(true);
            return;
        }

        Friendship fr = service.getFriendshipBetween(selected.getId(), service.getLoggedInUserId());

        sendRequestButton.setDisable(fr != null);
    }

    @FXML
    private void updateTable() {
        ArrayList<UserRelationshipView> views = new ArrayList<>();
        for (User user: service.getAllUsers()) {
            if (user.getId().equals(service.getLoggedInUserId())) {
                continue;
            }

            if (user.getLastName().toLowerCase().contains(search.getText().toLowerCase()) ||
                    user.getFirstName().toLowerCase().contains(search.getText().toLowerCase())) {

                Friendship fr = service.getFriendshipBetween(user.getId(), service.getLoggedInUserId());

                String relationship;

                if (fr == null) {
                    relationship = "not friends";
                } else if (fr.getStatus().equals("pending")) {
                    relationship = "pending";
                } else if (fr.getStatus().equals("accepted")) {
                    relationship = "friends";
                } else if (fr.getStatus().equals("declined")) {
                    relationship = "declined req.";
                } else {
                    relationship = "unknown";
                }

                views.add(new UserRelationshipView(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        relationship
                ));
            }
        }

        table.getItems().setAll(views);
        this.updateButtons();
    }


    @FXML
    private void back() throws IOException {
        App.setRoot("main_menu");
    }

    @FXML
    private void switchToMessages() throws IOException {
        App.setRoot("messages");

        UserRelationshipView selected = table.getSelectionModel().getSelectedItem();

        if (selected != null) {
            service.setMessagedUser(selected.getId());
        }

    }

    @FXML
    private void initialize() {
        TableColumn<UserRelationshipView, String> first_name = new TableColumn<>("First name");
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<UserRelationshipView, String> last_name = new TableColumn<>("Last name");
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<UserRelationshipView, String> relationship = new TableColumn<>("Relationship");
        relationship.setCellValueFactory(new PropertyValueFactory<>("relationship"));

        table.getColumns().addAll(first_name, last_name, relationship);
    }

    @Override
    public void setService(Service service) {
        this.service=service;
    }
}
