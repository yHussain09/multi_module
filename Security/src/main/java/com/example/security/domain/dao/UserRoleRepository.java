package com.example.security.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.security.domain.entities.UserRole;
import com.example.security.domain.entities.pks.UserRolePK;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends RepositoryBase<UserRole, UserRolePK> {
}
