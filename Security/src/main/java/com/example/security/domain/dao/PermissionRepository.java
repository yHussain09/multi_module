package com.example.security.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.security.domain.entities.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends RepositoryBase<Permission, Long> {
}
