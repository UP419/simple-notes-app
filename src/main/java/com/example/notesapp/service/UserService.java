package com.example.notesapp.service;

import com.example.notesapp.utils.PasswordUtils;
import com.example.notesapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.notesapp.repository.UserRepository;

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

    public User logIn(String userName, String password) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!PasswordUtils.verifyPassword(password, user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }
        return user;
    }
}
