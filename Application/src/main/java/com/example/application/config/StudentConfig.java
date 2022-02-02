package com.example.application.config;

import com.example.application.domain.dao.StudentRepository;
import com.example.application.domain.entities.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

@Configuration
public class StudentConfig {

    /*@Bean
    CommandLineRunner commandLineRunner (
            StudentRepository repository) {
        return args -> {
            Student student1 = new Student(
                    "Student 1",
                    "student1@email.com",
                    LocalDate.of(2000, Month.JANUARY, 5));
            Student student2 = new Student(
                    "Student 2",
                    "student2@email.com",
                    LocalDate.of(2005, Month.AUGUST, 19));

            repository.saveAll(Arrays.asList(student1, student2));
        };
    }*/
}
