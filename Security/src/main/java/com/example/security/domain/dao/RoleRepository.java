package com.example.security.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.security.domain.entities.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends RepositoryBase<Role, Long> {
}
