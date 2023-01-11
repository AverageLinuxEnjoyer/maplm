package social_network.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.checkerframework.checker.units.qual.A;
import social_network.App;
import social_network.controllers.views.MessageView;
import social_network.controllers.views.UserRelationshipView;
import social_network.domain.Message;
import social_network.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MessagesController implements Controller{
    private Service service;

    @FXML
    TableView<MessageView> table;

    @FXML
    TextField message_textfield;

    @FXML
    private void sendMessage() {
        String content = message_textfield.getText();
        message_textfield.clear();

        service.addMessage(service.getLoggedInUserId(), service.getMessagedUser(), content);
        this.updateTable();
    }

    @FXML
    private void initialize() {
        TableColumn<MessageView, String> sender = new TableColumn<>("Sender");
        sender.setCellValueFactory(new PropertyValueFactory<>("username1"));

        TableColumn<MessageView, String> receiver = new TableColumn<>("Receiver");
        receiver.setCellValueFactory(new PropertyValueFactory<>("username2"));

        TableColumn<MessageView, String> content = new TableColumn<>("Message");
        content.setCellValueFactory(new PropertyValueFactory<>("content"));

        TableColumn<MessageView, String> sent_at = new TableColumn<>("Sent at");
        sent_at.setCellValueFactory(new PropertyValueFactory<>("sent_at"));

        table.getColumns().addAll(sent_at, sender, receiver, content);
    }

    @FXML
    private void updateTable() {
        ArrayList<MessageView> res = new ArrayList<>();

        for (Message m : service.getAllMessagesBetween(service.getLoggedInUserId(), service.getMessagedUser())) {
            MessageView mv = new MessageView(
                    m.getId(),
                    m.getId1(),
                    service.getUser(m.getId1()).getFirstName(),
                    m.getId2(),
                    service.getUser(m.getId2()).getFirstName(),
                    m.getSent_at(),
                    m.getContent()
            );

            res.add(mv);
        }

        res.sort(Comparator.comparing(MessageView::getSent_at));

        table.getItems().setAll(res);
    }

    @FXML
    private void switchToUsers() throws IOException {
        App.setRoot("users");
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void update() {
        Controller.super.update();
        this.updateTable();
    }


}
