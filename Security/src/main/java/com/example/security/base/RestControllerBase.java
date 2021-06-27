package com.example.security.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

@PreAuthorize("hasRole('ROLE_USER')")
public abstract class RestControllerBase<T, ID extends Serializable> {

    protected static final Logger LOGGER = LoggerFactory.getLogger (RestControllerBase.class);

    protected final ServiceBase<T, ID> service;

    @Autowired
    public RestControllerBase(final ServiceBase<T, ID> service) {
        this.service = service;
    }

    @GetMapping("/")
    @PreAuthorize("hasPermission(null, 'READ')")
    public ResponseEntity<T> getAll(final Pageable pageable, final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> all of {0}", entity.getClass ().toString ()));
        return service.getAll (pageable, entity);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(null, 'READ')")
    public ResponseEntity<Optional<T>> getById(@PathVariable final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> by Id {0}", id));
        return service.getById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasPermission(null, 'CREATE')")
    public ResponseEntity<T> save(@RequestBody final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> save {0}", entity.toString ()));
        return service.save (entity);
    }

    /*@PostMapping("/")
    public List<T> saveAll(@RequestBody final List<T> entities) {
        LOGGER.info (String.format ("RestControllerBase --> save {0}", entities.get(0).toString ()));
        return service.saveAll (entities);
    }*/

    @PutMapping("/")
    @PreAuthorize("hasPermission(null, 'UPDATE')")
    public ResponseEntity<T> update(@RequestBody final T entity) {
        LOGGER.info(String.format("RestControllerBase --> update {0}", entity.toString()));
        return service.update(entity);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasPermission(null, 'DELETE')")
    public void delete(@RequestBody final T entity) {
        LOGGER.info (String.format ("RestControllerBase --> delete {0}", entity.toString ()));
        service.delete(entity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(null, 'DELETE')")
    public void delete(@PathVariable final ID id) {
        LOGGER.info (String.format ("RestControllerBase --> delete by Id {0}", id));
        service.deleteById(id);
    }
}
