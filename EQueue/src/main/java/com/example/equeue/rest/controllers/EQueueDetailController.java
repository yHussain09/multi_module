package com.example.equeue.rest.controllers;

import com.example.corejava.rest.controllers.RestControllerBase;
import com.example.equeue.domain.entities.EQueueDetail;
import com.example.equeue.domain.services.EQueueDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eq/equeueDetail")
public class EQueueDetailController extends RestControllerBase<EQueueDetail, Long> {
    public EQueueDetailController(EQueueDetailService service) {
        super(service);
    }
}
