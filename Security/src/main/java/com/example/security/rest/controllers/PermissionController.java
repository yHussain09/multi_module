package com.example.security.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.security.domain.entities.Permission;
import com.example.security.domain.services.PermissionService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/permission")
public class PermissionController extends RestControllerBase<Permission, Long> {
    public PermissionController(PermissionService service) {
        super(service);
    }
}
