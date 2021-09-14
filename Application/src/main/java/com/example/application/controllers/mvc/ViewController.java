package com.example.application.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ViewController")
public class ViewController {
    @RequestMapping(value = "/")
    public String contentMaster() {
        return "/index";
    }
}
