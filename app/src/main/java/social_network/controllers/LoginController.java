package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import social_network.App;
import social_network.domain.User;
import social_network.service.Service;

import java.io.IOException;

public class LoginController implements Controller {
    private Service service = null;

    @FXML
    Text message;

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Button loginButton;

    @FXML
    private void updateLoginButton() {
        loginButton.setDisable(email.getText().isEmpty() || password.getText().isEmpty());
    }

    @FXML
    private void login() throws IOException {
        String pass = String.valueOf(password.getText().hashCode());

        for (User user : service.getAllUsers()) {
            if (user.getEmail().equals(email.getText()) && user.getPassword().equals(pass)) {
                service.setLoggedInUserId(user.getId());
                App.setRoot("main_menu");
                return;
            }
        }

        message.setText("The email and password don't match.");
        message.setFill(Color.RED);
    }

    @FXML
    private void switchToRegister() throws IOException {
        service.addMessage(99L, 100L, "salut");
        App.setRoot("register");
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }
}
