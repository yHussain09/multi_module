package com.example.security.domain.dao;

import com.example.security.base.RepositoryBase;
//import com.example.demo.domain.entities.UserRole;
import com.example.security.domain.entities.RolePermission;
import com.example.security.domain.entities.pks.RolePermissionPK;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends RepositoryBase<RolePermission, RolePermissionPK> {
}
