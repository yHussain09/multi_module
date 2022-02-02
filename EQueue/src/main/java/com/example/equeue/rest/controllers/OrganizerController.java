package com.example.equeue.rest.controllers;

import com.example.corejava.domain.services.ServiceBase;
import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.equeue.domain.entities.Organizer;
import com.example.equeue.domain.services.OrganizerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eq/organizer")
public class OrganizerController extends RestControllerBase<Organizer, Long> {
    public OrganizerController(OrganizerService service) {
        super(service);
    }
}
