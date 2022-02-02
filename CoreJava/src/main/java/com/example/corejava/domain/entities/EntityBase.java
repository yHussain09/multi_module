package com.example.corejava.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityBase {

    @CreatedBy
    @Column(name = "created_by", length = 50, nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 6, nullable = false, updatable = false)    private Date createdDate;

    @LastModifiedBy
    @Column(name = "modified_by", length = 50, nullable = false)
    private String modifiedBy;

    @Version
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", length = 6, nullable = false)
    private Date modifiedDate;


    /*public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }*/
}
