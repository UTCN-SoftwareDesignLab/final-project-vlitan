package main.service;

import main.model.User;
import main.repository.UserRepository;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public Notification<Boolean> register(User user){
        Notification<Boolean> registerNotification = new Notification<>();
        String plainPassword = user.getPassword();
        user.setPassword(AuthenticationServiceImpl.encodePassword(plainPassword));
        user.setEnabled(true);
        if (userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()){
            registerNotification.addError("Already existing user with name " + user.getEmail() + " and same password");
            registerNotification.setResult(Boolean.FALSE);
        }
        else {
            Notification<Boolean> saveNotification = userService.save(user);
            registerNotification.setResult(saveNotification.getResult());
            registerNotification.addError(saveNotification.getFormattedErrors());
        }
        user.setPassword(plainPassword);
        return registerNotification;
    }

    public static String encodePassword(String password) {//TODO add this into a separate service
        return new ShaPasswordEncoder().encodePassword(password,null);
    }
}
