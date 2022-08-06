package com.example.btkmall.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class WebAppBtkMallController {
    @GetMapping(value = "/dashboard")
    public String getHome() {
        return "btk-mall-dashboard";
    }

}
