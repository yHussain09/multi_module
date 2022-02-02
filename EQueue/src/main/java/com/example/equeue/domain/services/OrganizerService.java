package com.example.equeue.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.corejava.domain.services.ServiceBase;
import com.example.equeue.domain.dao.OrganizerRepository;
import com.example.equeue.domain.entities.Organizer;
import org.springframework.stereotype.Service;

@Service
public class OrganizerService extends ServiceBase<Organizer, Long> {
    public OrganizerService(OrganizerRepository repository) {
        super(repository);
    }
}
