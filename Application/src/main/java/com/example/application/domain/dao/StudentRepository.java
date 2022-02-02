package com.example.application.domain.dao;

import com.example.application.domain.entities.Student;
import com.example.corejava.domain.dao.RepositoryBase;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface StudentRepository extends RepositoryBase<Student, Long> {
    Optional<Student> findStudentByEmail(String email);
}
