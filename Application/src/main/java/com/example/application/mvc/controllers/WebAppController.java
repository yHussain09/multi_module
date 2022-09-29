package com.example.application.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class WebAppController {
    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
