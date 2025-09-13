package dev.eirzarog.synthor.api.entities;

import dev.eirzarog.synthor.api.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "models", schema="public")
public class Model extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "provider", nullable = false, columnDefinition = "TEXT")
    private String provider;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();


    public Model(String name, String provider) {
        this.name = name;
        this.provider = provider;
    }

}
