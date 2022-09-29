package com.example.corejava.rest.controllers;

import com.example.corejava.domain.services.ServiceBase;
import com.example.corejava.dto.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
//@PreAuthorize("hasRole('ROLE_USER')")
public abstract class RestControllerBase<T, ID extends Serializable> {

    protected final ServiceBase<T, ID> service;

    public RestControllerBase(final ServiceBase<T, ID> service) {
        this.service = service;
    }

    @GetMapping("/")
//    @PreAuthorize("hasPermission(null, 'READ')")
    public ResponseEntity<RestResponse> getAll(final Pageable pageable, final T entity) {
        Map<String, Object> map = new HashMap<>();
        map.put(entity.getClass().getSimpleName(), this.service.getAll(pageable, entity));
        return ResponseEntity.ok(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
//                .data(Map.of("students", this.studentService.getAllStudents(30)))
                        .data(map)
                        .message(String.format("%s's list retrieved.", entity.getClass().getSimpleName()))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
//                        .path(ServletUriComponentsBuilder.getCurrentRequest().getServletPath())
                        .build()
        );
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasPermission(null, 'READ')")
    public ResponseEntity<RestResponse> getById(@PathVariable final ID id) {
        Map<String, Object> map = new HashMap<>();
        map.put(this.service.getClass().getSimpleName(), this.service.getById(id));
        return ResponseEntity.ok(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(map)
                        .message(String.format("%s retrieved by ID: %s.", "", id))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/")
//    @PreAuthorize("hasPermission(null, 'CREATE')")
    public ResponseEntity<RestResponse> save(@RequestBody final T entity) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/").toUriString());
        Map<String, Object> map = new HashMap<>();
        map.put(entity.getClass().getSimpleName(), this.service.save(entity));
        return ResponseEntity.created(uri).body(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(map)
                        .message(String.format("%s saved.", entity))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @PostMapping("/saveAll")
//    @PreAuthorize("hasPermission(null, 'CREATE')")
    public ResponseEntity<RestResponse> saveAll(@RequestBody final List<T> entities) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/saveAll").toUriString());
        Map<String, Object> map = new HashMap<>();
        map.put(this.service.getClass().getSimpleName(), this.service.saveAll(entities));
        return ResponseEntity.created(uri).body(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(map)
                        .message(String.format("%s savedAll.", entities))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @PutMapping("/")
//    @PreAuthorize("hasPermission(null, 'UPDATE')")
    public ResponseEntity<RestResponse> update(@RequestBody final T entity) {
        Map<String, Object> map = new HashMap<>();
        map.put(entity.getClass().getSimpleName(), this.service.update(entity));
        return ResponseEntity.ok(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(map)
                        .message(String.format("%s updated.", entity))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/")
//    @PreAuthorize("hasPermission(null, 'DELETE')")
    public ResponseEntity<RestResponse> delete(@RequestBody final T entity) {
        service.delete(entity);
        return ResponseEntity.accepted().body(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(String.format("%s deleted.", entity))
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasPermission(null, 'DELETE')")
    public ResponseEntity<RestResponse> delete(@PathVariable final ID id) {
        service.deleteById(id);
        return ResponseEntity.accepted().body(
                RestResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .message(String.format("Delete by ID: %s .", id))
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }
}
