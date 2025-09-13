package dev.eirzarog.synthor.api.repositories;


import dev.eirzarog.synthor.api.entities.Chat;
import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.enumerators.ChatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    // Find all active chats for a user (not deleted, not archived)
    List<Chat> findByUserIdAndDeletedFalseAndIsArchivedFalse(Long userId);

    // Find all chats for a user (including archived but not deleted)
    List<Chat> findByUserIdAndDeletedFalse(Long userId);

    // Find archived chats for a user
    List<Chat> findByUserIdAndDeletedFalseAndIsArchivedTrue(Long userId);

    // Find chat by ID and user (security check)
    Optional<Chat> findByIdAndUserIdAndDeletedFalse(Long id, Long userId);

    // Custom query to find chats with recent activity
    @Query("SELECT c FROM Chat c WHERE c.user.id = :userId AND c.deleted = false " +
            "ORDER BY c.updatedAt DESC")
    List<Chat> findRecentChatsByUserId(@Param("userId") Long userId);

    // Find chats by status
    List<Chat> findByUserIdAndStatusAndDeletedFalse(Long userId, ChatStatus status);

    // Count active chats for a user
    long countByUserIdAndDeletedFalseAndIsArchivedFalse(Long userId);


    //    @Query(nativeQuery = true,
//            value = "SELECT c.* " +
//                    "FROM public.chats c " +
//                    "   inner join public.users u on c.created_by = u.id " +
//                    "WHERE (:userId is null or c.created_by = :userId) AND " +
//                    "   (:username is null or u.username = :username) and " +
//                    "   (CAST(:from AS timestamp) is null or c.created_at >= :from) AND " +
//                    "   (CAST(:to AS timestamp) is null or c.created_at <= :to) ")
//    List<Chat> findAll(Long userId,
//                       String username,
//                       Instant from,
//                       Instant to);


    // same as above with JPA:
//    @Query("SELECT c FROM Chat c " +
//            "  JOIN c.user u " +
//            "WHERE (:userId is null or c.user.id = :userId) AND " +
//            "  (:username is null or u.username = :username) AND " +
//            "  (CAST(:from AS timestamp) is null or c.createdAt >= :from) AND " +
//            "  (CAST(:to AS timestamp) is null or c.createdAt <= :to)")
//    List<Chat> findAllWithJPA(Long userId,
//                       String username,
//                       Instant from,
//                       Instant to);
}
