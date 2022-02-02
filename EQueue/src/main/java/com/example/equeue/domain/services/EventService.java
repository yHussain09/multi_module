package com.example.equeue.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.corejava.domain.services.ServiceBase;
import com.example.equeue.domain.dao.EventRepository;
import com.example.equeue.domain.entities.Event;
import org.springframework.stereotype.Service;

@Service
public class EventService extends ServiceBase<Event, Long> {
    public EventService(EventRepository repository) {
        super(repository);
    }
}
