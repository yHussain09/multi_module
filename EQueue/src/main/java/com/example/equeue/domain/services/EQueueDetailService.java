package com.example.equeue.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.corejava.domain.services.ServiceBase;
import com.example.equeue.domain.dao.EQueueDetailRepository;
import com.example.equeue.domain.entities.EQueueDetail;
import org.springframework.stereotype.Service;

@Service
public class EQueueDetailService extends ServiceBase<EQueueDetail, Long> {
    public EQueueDetailService(EQueueDetailRepository repository) {
        super(repository);
    }
}
