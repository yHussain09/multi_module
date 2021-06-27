package com.example.security.domain.dao;

import com.example.security.base.RepositoryBase;
import com.example.security.domain.entities.UserRole;
import com.example.security.domain.entities.pks.UserRolePK;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends RepositoryBase<UserRole, UserRolePK> {
}
