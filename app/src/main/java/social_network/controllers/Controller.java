package social_network.controllers;

import social_network.service.Service;

public interface Controller {
    void setService(Service service);
    default void update() {

    }
}
