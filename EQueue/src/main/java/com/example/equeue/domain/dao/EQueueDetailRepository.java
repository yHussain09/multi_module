package com.example.equeue.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.equeue.domain.entities.EQueueDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface EQueueDetailRepository extends RepositoryBase<EQueueDetail, Long> {
}
