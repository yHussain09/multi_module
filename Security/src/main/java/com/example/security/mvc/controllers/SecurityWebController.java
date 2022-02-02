package com.example.security.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping(value = "/app")
public class SecurityWebController {

    @GetMapping(value = "/login")
    public String getLogin(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping(value = "/")
    public String getHome() {
        return "index";
    }

    /*@PostMapping(value = "/user/login")
    public String postLogin() {
        return "login";
    }
*/
    @GetMapping(value = "/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping(value = "/user/register")
    public String postRegister() {
        return "register";
    }

    /*@RequestMapping(value = "/error")
    public String error() {
        return "error";
    }*/

    @GetMapping(value = "/user_view")
    public String getUserView() {
        return "user_view";
    }

    @GetMapping(value = "/role_view")
    public String getRoleView() {
        return "role_view";
    }

    @GetMapping(value = "/permission_view")
    public String getPermissionView() {
        return "permission_view";
    }
}
