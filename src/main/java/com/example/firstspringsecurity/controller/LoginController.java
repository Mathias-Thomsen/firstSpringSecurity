package com.example.firstspringsecurity.controller;

import com.example.firstspringsecurity.entity.User;
import com.example.firstspringsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getCustomers() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println(user.toString());
        return userService.registerUser(user);
    }

}
