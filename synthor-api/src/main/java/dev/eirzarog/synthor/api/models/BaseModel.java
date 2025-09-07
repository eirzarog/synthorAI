package dev.eirzarog.synthor.api.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // No setter for id
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE) // No setter for createdAt
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Instant updatedAt;

    //Prevents conflicting profile updates
    @Version
    @Setter(AccessLevel.NONE)  // No setter for version
    @Column(name = "version", nullable = false, updatable = false)
    private Long version;

// @PrePersist
// protected void onCreate() {
//     createdAt = Instant.now();
//     updatedAt = Instant.now();
// }
//
// @PreUpdate
// protected void onUpdate() {
//     updatedAt = Instant.now();
// }

}
