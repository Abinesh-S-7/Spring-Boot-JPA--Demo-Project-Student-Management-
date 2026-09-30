package dev.student.project.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


// can have common values used later
@MappedSuperclass
public abstract class Base {


    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    void onCreate()
    {
        createdAt = modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate()
    {
        modifiedAt = LocalDateTime.now();
    }
}
