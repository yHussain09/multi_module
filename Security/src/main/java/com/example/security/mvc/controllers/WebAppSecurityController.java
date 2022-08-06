package com.example.security.mvc.controllers;

import com.example.security.domain.entities.User;
import com.example.security.domain.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequestMapping(value = "/app")
public class WebAppSecurityController {
    private UserService userService;

    public WebAppSecurityController( UserService userService) {
        this.userService = userService;
    }

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

    /*@PostMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }*/

    /*@GetMapping(value = "/")
    public String getHome() {
        return "index";
    }*/

    /*@PostMapping(value = "/user/login")
    public String postLogin() {
        return "login";
    }
*/
    @GetMapping(value = "/register")

    public String getRegister(Model model) {
        model.addAttribute("User", new User());
        return "register";
    }

    @PostMapping(value = "/user/register")
    public String postRegister(@ModelAttribute("User") User user, Model model) {
        user.setEnabled("Y");
        user.setActivated("Y");
        user = userService.save(user);

        if(user.getId() != null) {
            model.addAttribute("message", "User successfully created!");
            return "redirect:/app/home";
        }
        else {
            model.addAttribute("message", "Error!");
            return "register";
        }
    }

    /*@RequestMapping(value = "/error")
    public String error() {
        return "error";
    }*/

    @GetMapping(value = "/user")
    public String getUserView() {
        return "user_view";
    }

    @GetMapping(value = "/role")
    public String getRoleView() {
        return "role_view";
    }

    @GetMapping(value = "/permission")
    public String getPermissionView() {
        return "permission_view";
    }
}
