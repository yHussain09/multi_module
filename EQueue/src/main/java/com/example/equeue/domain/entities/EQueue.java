package com.example.equeue.domain.entities;

import com.example.corejava.domain.entities.EntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "EQU_QUEUE")
public class EQueue extends EntityBase {
    @Id
    @SequenceGenerator(
            name = "equeue_sequence",
            sequenceName = "equeue_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equeue_sequence"
    )
    private Long id;
    private Long organizerId;
    private Long eventId;
    private Long parentEventId;
    private String name;
    private Date elapsedTime;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getParentEventId() {
        return parentEventId;
    }

    public void setParentEventId(Long parentEventId) {
        this.parentEventId = parentEventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Date elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
