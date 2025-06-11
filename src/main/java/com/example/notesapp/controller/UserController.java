package com.example.notesapp.controller;

import com.example.notesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UUID> singUp(@RequestBody AuthRequest authRequest) {
        UUID userId = userService.signUp(authRequest.userName, authRequest.password).getId();
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UUID> logIn(@RequestBody AuthRequest authRequest) {
        UUID userId = userService.logIn(authRequest.userName, authRequest.password).getId();
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    public record AuthRequest(String userName, String password) {
    }

}
