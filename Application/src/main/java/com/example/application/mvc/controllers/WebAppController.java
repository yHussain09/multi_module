package com.example.application.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class WebAppController //implements MvcControllerBase
{
    @GetMapping(value = "/home")
    public String abc() {
        return "abc";
    }
}
