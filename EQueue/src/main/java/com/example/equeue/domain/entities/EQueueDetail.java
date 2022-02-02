package com.example.equeue.domain.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EQU_QUEUE_DETAIL")
public class EQueueDetail {
    @Id
    @SequenceGenerator(
            name = "equeue_detail_sequence",
            sequenceName = "equeue_detail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equeue_detail_sequence"
    )
    private Long id;
    private Long organizerId;
    private Long eventId;
    private Long parentEventId;
    private Long eQueueId;
    private String name;
    private Long previous;
    private Long eQueueNumber;
    private Long next;

    @Transient
    private Date waitingTime;
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

    public Long geteQueueId() {
        return eQueueId;
    }

    public void seteQueueId(Long eQueueId) {
        this.eQueueId = eQueueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(Long previous) {
        this.previous = previous;
    }

    public Long geteQueueNumber() {
        return eQueueNumber;
    }

    public void seteQueueNumber(Long eQueueNumber) {
        this.eQueueNumber = eQueueNumber;
    }

    public Long getNext() {
        return next;
    }

    public void setNext(Long next) {
        this.next = next;
    }

    public Date getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Date waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
