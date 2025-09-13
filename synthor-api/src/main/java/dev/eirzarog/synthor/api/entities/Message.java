package dev.eirzarog.synthor.api.entities;


import dev.eirzarog.synthor.api.entities.base.BaseEntity;
import dev.eirzarog.synthor.api.enumerators.MessageSender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages", schema="public")
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_sender", nullable = false, columnDefinition = "TEXT")
    private MessageSender messageSender = MessageSender.USER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;



    public Message(Chat chat, User user, String content) {
        this.chat = chat;
        this.user = user;
        this.content = content;
        this.messageSender = MessageSender.USER;
    }

    public Message(Chat chat, Model model, String content) {
        this.chat = chat;
        this.model = model;
        this.content = content;
        this.messageSender = MessageSender.MODEL;
    }

    // Constructor for system messages
    public Message(Chat chat, String content) {
        this.chat = chat;
        this.content = content;
        this.messageSender = MessageSender.SYSTEM;
        // user and model remain null for system messages
    }



}