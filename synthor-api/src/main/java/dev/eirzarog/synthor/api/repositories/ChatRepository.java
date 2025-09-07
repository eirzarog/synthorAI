package dev.eirzarog.synthor.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface ChatRepository  {

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
