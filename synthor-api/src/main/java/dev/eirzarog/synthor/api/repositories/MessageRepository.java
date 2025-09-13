package dev.eirzarog.synthor.api.repositories;

import dev.eirzarog.synthor.api.entities.Message;
import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.enumerators.MessageSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
    List<Message> findByChatIdAndMessageSender(Long chatId, MessageSender sender);
    long countByChatId(Long chatId);


}
