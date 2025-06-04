package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("")
//    public User singUp() {
//
//    }
//
//    @PostMapping("")
//    public ResponseEntity<String> logIn() {
//
//    }

}
