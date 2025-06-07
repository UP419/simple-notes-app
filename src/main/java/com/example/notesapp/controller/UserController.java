package com.example.notesapp.controller;

import com.example.notesapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.notesapp.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> singUp(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.signUp(authRequest.userName, authRequest.password), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> logIn(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userService.logIn(authRequest.userName, authRequest.password), HttpStatus.OK);
    }

    public record AuthRequest(String userName, String password){}

}
