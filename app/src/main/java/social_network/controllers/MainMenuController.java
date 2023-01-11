package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import social_network.App;
import social_network.domain.User;
import social_network.service.Service;

import java.io.IOException;

public class MainMenuController implements Controller {
    private Service service = null;

    @FXML
    private Text loggedInAs;

    @FXML
    private void switchToFriends() throws IOException {
        App.setRoot("friends");
    }

    @FXML
    private void switchToUsers() throws IOException {
        App.setRoot("users");
    }

    @FXML
    private void switchToFriendRequests() throws IOException {
        App.setRoot("friend_requests");
    }
    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void update() {
        Controller.super.update();
        User logged_user = service.getUser(service.getLoggedInUserId());
        String text = "Logged in as " + logged_user.getLastName() + " " + logged_user.getFirstName();

        loggedInAs.setText(text);
    }
}
