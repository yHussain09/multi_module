package com.example.global.rest.controllers;

import com.example.global.domain.entities.Organization;
import com.example.global.domain.services.OrganizationService;
import com.example.corejava.rest.controllers.RestControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController extends RestControllerBase<Organization, Long> {
    public OrganizationController(OrganizationService service) {
        super(service);
    }
}
