package com.example.security.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.security.domain.entities.UserRole;
//import com.example.demo.domain.services.UserAuthorityService;
import com.example.security.domain.entities.pks.UserRolePK;
import com.example.security.domain.services.UserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/userRole")
public class UserRoleController extends RestControllerBase<UserRole, UserRolePK> {
    public UserRoleController(UserRoleService service) {
        super(service);
    }
}
