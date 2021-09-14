package com.example.security.domain.services;

import com.example.corejava.domain.services.ServiceBase;
import com.example.security.domain.dao.RoleRepository;
import com.example.security.domain.entities.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceBase<Role, Long> {
    public RoleService(RoleRepository repository) {
        super(repository);
    }
}
