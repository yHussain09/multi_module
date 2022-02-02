package com.example.equeue.rest.controllers;

import com.example.corejava.domain.services.ServiceBase;
import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.equeue.domain.entities.EQueue;
import com.example.equeue.domain.services.EQueueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eq/equeue")
public class EQueueController extends RestControllerBase<EQueue, Long> {
    public EQueueController(EQueueService service) {
        super(service);
    }
}
