package com.example.equeue.rest.controllers;

import com.example.corejava.domain.services.ServiceBase;
import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.equeue.domain.entities.Event;
import com.example.equeue.domain.services.EventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eq/event")
public class EventController extends RestControllerBase<Event, Long> {
    public EventController(EventService service) {
        super(service);
    }
}
