package com.example.api.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ViewController")
public class ViewController {
    @RequestMapping(value = "/")
    public String contentMaster() {
        return "/index";
    }
}
