package dev.eirzarog.synthor.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eirzarog.synthor.api.entities.base.BaseEntity;
import dev.eirzarog.synthor.api.entities.base.SoftDeletableEntity;
import dev.eirzarog.synthor.api.enumerators.ChatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chats", schema="public")
public class Chat extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "TEXT")
    private ChatStatus status = ChatStatus.ACTIVE;

    @Column(name = "is_archived")
    private boolean isArchived = false;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Chat(User user, String title) {
        this.user = user;
        this.title = title;
    }

//    // Helper methods
//    public void addMessage(Message message) {
//        messages.add(message);
//        message.setChat(this);
//    }
//
//    public void removeMessage(Message message) {
//        messages.remove(message);
//        message.setChat(null);
//    }

}