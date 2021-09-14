package com.example.security.domain.services;

import com.example.corejava.domain.services.ServiceBase;
import com.example.security.domain.dao.RolePermissionRepository;
import com.example.security.domain.entities.RolePermission;
import com.example.security.domain.entities.pks.RolePermissionPK;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService extends ServiceBase<RolePermission, RolePermissionPK> {
    public RolePermissionService(RolePermissionRepository repository) {
        super(repository);
    }
}
