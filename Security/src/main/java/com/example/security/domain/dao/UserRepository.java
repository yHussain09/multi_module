package com.example.security.domain.dao;

import com.example.corejava.domain.dao.RepositoryBase;
import com.example.security.domain.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends RepositoryBase<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByEmailIgnoreCase(String email);

    Optional<User> findOneByUsername(String username);
}
