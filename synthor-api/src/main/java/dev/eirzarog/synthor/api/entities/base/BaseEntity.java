package dev.eirzarog.synthor.api.entities.base;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {


    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;



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
