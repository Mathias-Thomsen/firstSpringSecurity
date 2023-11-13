package com.example.firstspringsecurity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllAccess {



    @GetMapping("allAccess")
    public String getAllAccessSite() {
        return "This is a site all have access to";
    }
}
