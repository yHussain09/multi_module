package com.example.application.domain.services;

import com.example.application.domain.dao.StudentRepository;
import com.example.application.domain.entities.Student;
import com.example.corejava.domain.services.ServiceBase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Service
//@RequiredArgsConstructor
public class StudentService extends ServiceBase<Student, Long> {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository repository) {
        super(repository);
        this.studentRepository = repository;
    }

    @Override
    public Student save(Student entity) {
        Optional<Student> studentOptional = this.studentRepository.findStudentByEmail(entity.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("This is email is already taken.");
        }
        return super.save(entity);
    }

    public Collection<Student> getAllStudents(int limit) {
        return this.studentRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    private String setStudentImageUrl() {
        String[] imageNames = {"img1", "img2", "img3", "img4"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
