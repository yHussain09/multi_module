package com.example.equeue.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.equeue.domain.entities.EQueue;
import org.springframework.stereotype.Repository;

@Repository
public interface EQueueRepository extends RepositoryBase<EQueue, Long> {
}
