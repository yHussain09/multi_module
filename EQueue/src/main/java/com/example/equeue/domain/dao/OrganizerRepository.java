package com.example.equeue.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.equeue.domain.entities.Organizer;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRepository extends RepositoryBase<Organizer, Long> {
}
