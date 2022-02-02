package com.example.application.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    @GetMapping("/")
    public String apiTest(){
        return "Api Test Controller is working....";
    }
}
