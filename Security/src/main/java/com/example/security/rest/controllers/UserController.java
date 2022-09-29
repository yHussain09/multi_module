package com.example.security.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.security.domain.entities.Permission;
import com.example.security.domain.entities.Role;
import com.example.security.domain.entities.User;
import com.example.security.domain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController extends RestControllerBase<User, Long> {


    private final UserService userService;

    public UserController(UserService service) {
        super(service);
        this.userService = service;
    }

    @PostMapping("/register")
    public User saveUser(User user) {
        Set<Permission> permissions = Arrays.asList(new Permission("READ"),
                new Permission("CREATE"),
                new Permission("UPDATE"),
                new Permission("DELETE")).stream().collect(Collectors.toSet());
        user.setRoles(Arrays.asList(new Role("ROLE_USER", permissions)).stream().collect(Collectors.toSet()));
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
