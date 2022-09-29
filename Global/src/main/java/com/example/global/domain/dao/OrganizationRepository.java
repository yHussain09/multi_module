package com.example.global.domain.dao;

import com.example.global.domain.entities.Organization;
import com.example.corejava.domain.dao.RepositoryBase;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends RepositoryBase<Organization, Long> {
}



