package com.example.security.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.security.domain.entities.Role;
import com.example.security.domain.services.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/role")
public class RoleController extends RestControllerBase<Role, Long> {
    public RoleController(RoleService service) {
        super(service);
    }
}
