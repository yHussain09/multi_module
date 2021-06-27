package com.example.security.rest.controllers;

import com.example.security.base.RestControllerBase;
//import com.example.demo.domain.entities.UserRole;
//import com.example.demo.domain.services.UserRoleService;
import com.example.security.domain.entities.RolePermission;
import com.example.security.domain.entities.pks.RolePermissionPK;
import com.example.security.domain.services.RolePermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rolePermission")
public class RolePermissionController extends RestControllerBase<RolePermission, RolePermissionPK> {
    public RolePermissionController(RolePermissionService service) {
        super(service);
    }
}
