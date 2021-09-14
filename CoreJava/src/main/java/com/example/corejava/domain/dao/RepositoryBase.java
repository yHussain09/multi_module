package com.example.corejava.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface RepositoryBase<T, ID extends Serializable> extends JpaRepository<T, ID> {
}
