package com.example.security.domain.services;

import com.example.corejava.domain.services.ServiceBase;
import com.example.security.domain.dao.UserRoleRepository;
import com.example.security.domain.entities.UserRole;
import com.example.security.domain.entities.pks.UserRolePK;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends ServiceBase<UserRole, UserRolePK> {
    public UserRoleService(UserRoleRepository repository) {
        super(repository);
    }
}
