package com.example.security.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.security.domain.entities.User;
import com.example.security.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController extends RestControllerBase<User, Long> {


    private final UserService userService;

    public UserController(UserService service) {
        super(service);
        this.userService = service;
    }

    @PostMapping("/register")
    public User saveUser (User user){
        return this.userService.save(user);
    }





    /*@GetMapping("/")
    public List<User> getAllUsers (){
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserByUserId (@PathVariable String id) throws UserPrincipalNotFoundException {
        return this.userService.getUserById(id);
    }



    @PutMapping("/")
    public User updateUser(User user){
        return this.userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable String id){
        this.userService.deleteUserById(id);
    }*/
}
