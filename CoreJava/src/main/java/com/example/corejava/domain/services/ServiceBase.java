package com.example.corejava.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class ServiceBase<T, ID extends Serializable> {

    private Class<T> entityClass;
    protected final RepositoryBase<T, ID> repository;

//    protected final ISession session;
//    @Value("${spring.mvc.date-format}")
//    private String dateFormat;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected JdbcTemplate DB;

//    @Autowired
//    protected ColumnMetaService columnMetaService;
/*
    /*@Autowired
    protected EntityManager entityManager;*/

//    private CriteriaQuery<Tuple> tupleQuery;
//    private CriteriaQuery<Long> countQuery;
//    private CriteriaQuery<T> entityQuery;
//    private Boolean organizationBased;
//    private Boolean locationBased;

//    @Autowired
    public ServiceBase(final RepositoryBase<T, ID> repository) {
//        this.session = session;
        this.repository = repository;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Page<T> getAll(final Pageable pageable, T entity) {
        log.info(String.format("ServiceBase --> getAll of {%s}", entity.getClass().toString()));
        return this.repository.findAll(pageable);
    }

    public ResponseEntity<Optional<T>> getById(final ID id) {
        log.info(String.format("ServiceBase --> getById for {%s}", id));
        return ResponseEntity.ok(this.repository.findById(id));
    }

    public T save(final T entity) {
        log.info(String.format("ServiceBase --> save for {%s}", entity.toString()));
        return this.repository.save(entity);
    }

    public T saveAndFlush(final T entity) {
        log.info(String.format("ServiceBase --> saveAndFlush for {%s}", entity.toString()));
        return this.repository.saveAndFlush(entity);
    }

    public T update(final T entity) {
        log.info(String.format("ServiceBase --> update for {%s}", entity.toString()));
        return this.repository.save(entity);
    }

    public List<T> saveAll(final List<T> entity) {
        log.info(String.format("ServiceBase --> saveAll for {%s}", entity.toString()));
        return this.repository.saveAll(entity);
    }

    public void delete(final T entity) {
        boolean exists = this.repository.exists(Example.of(entity));
        if(!exists) {
            throw new EntityNotFoundException("Entity not found!");
        }
        log.info(String.format("ServiceBase --> delete for {%s}", entity.toString()));
        this.repository.delete(entity);
    }

    public void deleteById(final ID id) {
        boolean exists = this.repository.existsById(id);
        if(!exists) {
            throw new EntityNotFoundException(String.format("%s not found by ID: {%s}", this.entityClass.getSimpleName(), id));
        }
        log.info(String.format("Service --> deleteById for {%s}", id));
        this.repository.deleteById(id);
    }


}
