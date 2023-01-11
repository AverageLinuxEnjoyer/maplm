package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.postgresql.util.PSQLException;
import social_network.App;
import social_network.service.Service;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController implements Controller {
    private Service service = null;

    @FXML
    Text message;

    @FXML
    TextField email;

    @FXML
    TextField first_name;

    @FXML
    TextField last_name;

    @FXML
    PasswordField password;

    @FXML
    PasswordField confirm_password;

    @FXML
    Button register_button;

    @FXML
    private void updateRegisterButton() {
        register_button.setDisable(email.getText().equals("") ||
                first_name.getText().equals("") ||
                last_name.getText().equals("") ||
                password.getText().equals("") ||
                confirm_password.getText().equals(""));
    }


    @FXML
    private void register() {
        if (!password.getText().equals(confirm_password.getText())) {
            message.setFill(Color.RED);
            message.setText("Passwords don't match.");
            return;
        }

        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            message.setFill(Color.RED);
            message.setText("Invalid email.");
            return;
        }

        try {
            service.addUser(first_name.getText(), last_name.getText(), email.getText(), 0L, password.getText());
        } catch (Exception e) {
            message.setText(e.getMessage());
            message.setFill(Color.RED);
            return;
        }

        message.setFill(Color.GREEN);
        message.setText("Account created.");

    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }
}
