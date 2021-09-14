package com.example.security.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
//import com.example.demo.domain.entities.UserRole;
import com.example.security.domain.entities.RolePermission;
import com.example.security.domain.entities.pks.RolePermissionPK;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends RepositoryBase<RolePermission, RolePermissionPK> {
}
