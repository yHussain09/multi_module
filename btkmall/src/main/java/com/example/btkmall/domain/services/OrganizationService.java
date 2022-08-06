package com.example.btkmall.domain.services;

import com.example.btkmall.domain.dao.OrganizationRepository;
import com.example.btkmall.domain.entities.Organization;
import com.example.corejava.domain.services.ServiceBase;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService extends ServiceBase<Organization, Long>{
    public OrganizationService(OrganizationRepository repository) {
        super(repository);
    }
}
