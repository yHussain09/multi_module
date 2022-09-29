package com.example.application.rest.controllers;

import com.example.application.domain.entities.Response;
import com.example.application.domain.entities.Student;
import com.example.application.domain.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

//@CrossOrigin
@RestController
@RequestMapping("/api/student")
//@RequiredArgsConstructor
public class StudentController //extends RestControllerBase<Student, Long>
{
    private final StudentService studentService;

    public StudentController(StudentService service) {
//        super(service);
        this.studentService = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getStudentResponse() {
        Map<String, Collection> map = new HashMap<>();
        map.put("students", this.studentService.getAllStudents(30));

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
//                .data(Map.of("students", this.studentService.getAllStudents(30)))
                        .data(map)
                        .message("Student's list retrieved.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveStudent(@RequestBody @Valid Student student) {
        Map<String, Student> map = new HashMap<>();
//        map.put("student", this.studentService.save(student).getBody());
        map.put("student", this.studentService.save(student));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/save").toUriString());
        return ResponseEntity.created(uri).body(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
//                        .data(Map.of("student", ))
                        .data(map)
                        .message("Student created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    /*@GetMapping("/get/{id}")
    public ResponseEntity<Response> saveStudent(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("student", this.studentService.getById(id)))
                        .message("Student retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteStudent(@PathVariable Long id) {
//        Map<String, Student> map = new HashMap<>();
//        map.put("student", this.studentService.deleteById(id));
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
//                        .data(Map.of("student", this.studentService.deleteById(id)))
                        .message(String.format("Student: %s deleted", id.toString()))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getStudentImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
    }
}
