package com.example.equeue.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.corejava.domain.services.ServiceBase;
import com.example.equeue.domain.dao.EQueueRepository;
import com.example.equeue.domain.entities.EQueue;
import org.springframework.stereotype.Service;

@Service
public class EQueueService extends ServiceBase<EQueue, Long> {
    public EQueueService(EQueueRepository repository) {
        super(repository);
    }
}
