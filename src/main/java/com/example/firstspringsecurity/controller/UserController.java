package com.example.firstspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @GetMapping("/user")
    public String getUserAccessSite() {
        return "This is user access site";
    }
}
