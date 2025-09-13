package dev.eirzarog.synthor.api.services;


import dev.eirzarog.synthor.api.entities.Chat;
import dev.eirzarog.synthor.api.entities.Message;
import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.entities.criteria.ChatCriteria;
import dev.eirzarog.synthor.api.entities.dtos.ChatDTO;
import dev.eirzarog.synthor.api.entities.dtos.mappers.ChatMapper;
import dev.eirzarog.synthor.api.exceptions.GlobalException;
import dev.eirzarog.synthor.api.repositories.ChatRepository;
import dev.eirzarog.synthor.api.repositories.MessageRepository;
import dev.eirzarog.synthor.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       MessageRepository messageRepository,
                       UserRepository userRepositor,
                       ChatMapper chatMapper)
    {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepositor;
        this.chatMapper = chatMapper;
    }

    public List<ChatDTO> getAll(ChatCriteria criteria) throws GlobalException {

        Instant twoYearsAgo = Instant.now().minusSeconds(60 * 60 * 24 * 365 * 2);

        if (criteria.getFrom() != null && criteria.getFrom().isBefore(twoYearsAgo)) {
            throw new GlobalException(HttpStatus.BAD_REQUEST,
                    "The 'from' date cannot be more than two years ago.");
        }

        return chatRepository.findAll()
                .stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList());


    }








/*
    // CORRECTED VERSION - handles Optional properly
    public void archiveChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found with id: " + chatId));

        chat.setIsArchived(true);

        // System notification about archiving
        Message archiveMsg = new Message(chat, "This chat has been archived.");
        messageRepository.save(archiveMsg);

        chatRepository.save(chat);
    }

    // Better version with user security check
    public void archiveChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findByIdAndUserIdAndDeletedFalse(chatId, userId)
                .orElseThrow(() -> new RuntimeException("Chat not found or access denied"));

        chat.setIsArchived(true);

        // System notification about archiving
        Message archiveMsg = new Message(chat, "This chat has been archived.");
        messageRepository.save(archiveMsg);

        chatRepository.save(chat);
    }*/

    // Create new chat
    public Chat createNewChat(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Chat chat = new Chat(user, title);
        chat = chatRepository.save(chat);

        // Welcome message when chat is created
        Message welcomeMsg = new Message(chat, "Chat started. How can I help you today?");
        messageRepository.save(welcomeMsg);

        return chat;
    }

    // Get user's active chats
    public List<Chat> getUserActiveChats(Long userId) {
        return chatRepository.findByUserIdAndDeletedFalseAndIsArchivedFalse(userId);
    }

    // Get user's archived chats
    public List<Chat> getUserArchivedChats(Long userId) {
        return chatRepository.findByUserIdAndDeletedFalseAndIsArchivedTrue(userId);
    }

    // Soft delete a chat
    public void deleteChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findByIdAndUserIdAndDeletedFalse(chatId, userId)
                .orElseThrow(() -> new RuntimeException("Chat not found or access denied"));

        chat.setDeleted(true);

        // System notification about deletion
        Message deleteMsg = new Message(chat, "This chat has been deleted.");
        messageRepository.save(deleteMsg);

        chatRepository.save(chat);
    }
/*
    // Unarchive a chat
    public void unarchiveChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findByIdAndUserIdAndDeletedFalse(chatId, userId)
                .orElseThrow(() -> new RuntimeException("Chat not found or access denied"));

        chat.setIsArchived(false);

        // System notification about unarchiving
        Message unarchiveMsg = new Message(chat, "This chat has been restored from archive.");
        messageRepository.save(unarchiveMsg);

        chatRepository.save(chat);
    }*/

}
