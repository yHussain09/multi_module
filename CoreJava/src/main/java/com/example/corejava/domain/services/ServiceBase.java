package com.example.corejava.domain.services;

import com.example.corejava.domain.dao.RepositoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class ServiceBase<T, ID extends Serializable> {

    private Class<T> entityClass;
    protected final RepositoryBase<T, ID> repository;

//    protected final ISession session;
//    @Value("${spring.mvc.date-format}")
//    private String dateFormat;

    @PersistenceContext
    protected EntityManager entityManager;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceBase.class);

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

    public ResponseEntity<T> getAll(final Pageable pageable, T entity) {
        LOGGER.info(String.format("ServiceBase --> getAll of {%s}", entity.getClass().toString()));
        return (ResponseEntity<T>) ResponseEntity.ok(this.repository.findAll(pageable));
    }

    public ResponseEntity<Optional<T>> getById(final ID id) {
        LOGGER.info(String.format("ServiceBase --> getById for {%s}", id));
        return ResponseEntity.ok(this.repository.findById(id));
    }

    public ResponseEntity<T> save(final T entity) {
        LOGGER.info(String.format("ServiceBase --> save for {%s}", entity.toString()));
        return ResponseEntity.ok(this.repository.save(entity));
    }

    public ResponseEntity<T> saveAndFlush(final T entity) {
        LOGGER.info(String.format("ServiceBase --> saveAndFlush for {%s}", entity.toString()));
        return ResponseEntity.ok(this.repository.saveAndFlush(entity));
    }

    public ResponseEntity<T> update(final T entity) {
        LOGGER.info(String.format("ServiceBase --> update for {%s}", entity.toString()));
        return ResponseEntity.ok(this.repository.save(entity));
    }

    public ResponseEntity<List<T>> saveAll(final List<T> entity) {
        LOGGER.info(String.format("ServiceBase --> saveAll for {%s}", entity.toString()));
        return ResponseEntity.ok(this.repository.saveAll(entity));
    }

    public void delete(final T entity) {
        LOGGER.info(String.format("ServiceBase --> delete for {%s}", entity.toString()));
        this.repository.delete(entity);
    }

    public void deleteById(final ID id) {
        LOGGER.info(String.format("Service --> deleteById for {%s}", id));
        this.repository.deleteById(id);
    }


}
