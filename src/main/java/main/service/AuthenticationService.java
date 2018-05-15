package main.service;

import main.model.User;
import main.util.Notification;

public interface AuthenticationService {
    Notification<Boolean> register(User user);
}
