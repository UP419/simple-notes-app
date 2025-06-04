package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import utils.PasswordUtils;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signUp(String userName, String password) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User with this username already exists");
        }
        User newUser = new User(userName, PasswordUtils.encryptPassword(password));
        userRepository.save(newUser);
        return newUser;
    }
}
