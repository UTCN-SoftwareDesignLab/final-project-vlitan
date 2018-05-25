package main.service;

import main.model.User;
import main.repository.UserRepository;
import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Notification<Boolean> save(User user) {
        return AbstractRepoAdapter.save(userRepository, user);
    }

    @Override
    public Notification<Boolean> delete(User user) {
        return AbstractRepoAdapter.delete(userRepository, user);
    }


    @Override
    public Notification<Boolean> deleteById(Integer id) {
        return AbstractRepoAdapter.deleteById(userRepository, id);
    }
}

