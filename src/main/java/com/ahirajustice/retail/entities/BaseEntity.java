package com.ahirajustice.retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private boolean isDeleted;

    @PrePersist
    public void autoSetCreatedOn() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void autoSetUpdatedOn() {
        this.updatedOn = LocalDateTime.now();
    }

}
