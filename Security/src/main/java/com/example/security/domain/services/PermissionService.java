package com.example.security.domain.services;

import com.example.security.base.ServiceBase;
import com.example.security.domain.dao.PermissionRepository;
import com.example.security.domain.entities.Permission;
import org.springframework.stereotype.Service;


@Service
public class PermissionService extends ServiceBase<Permission,Long> {
    public PermissionService(PermissionRepository repository) {
        super(repository);
    }
}
