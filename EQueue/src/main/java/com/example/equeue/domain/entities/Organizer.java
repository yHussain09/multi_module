package com.example.equeue.domain.entities;

import com.example.corejava.domain.entities.EntityBase;

import javax.persistence.*;

@Entity
@Table(name = "EQU_ORGANIZER")
public class Organizer extends EntityBase {

    @Id
    @SequenceGenerator(
            name = "organizer_sequence",
            sequenceName = "organizer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "organizer_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
