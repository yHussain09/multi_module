package com.example.equeue.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.equeue.domain.entities.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends RepositoryBase<Event, Long> {
}
