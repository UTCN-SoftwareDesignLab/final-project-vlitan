package main.service;

import main.model.User;
import main.util.Notification;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Notification<Boolean> save(User user);
    Notification<Boolean> delete(User user);
    Notification<Boolean> deleteById(Integer id);
}
