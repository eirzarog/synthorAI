package dev.eirzarog.synthor.api.repositories;

import dev.eirzarog.synthor.api.enums.UserStatus;
import dev.eirzarog.synthor.api.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


//    SELECT COUNT(*) > 0 FROM users WHERE username = ?
//    It returns true if a record with that username exists, otherwise false
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByPassword(String password);

//    Check and this
//    Page<User> findByStatus(UserStatus status, Pageable pageable);

//    Executes raw SQL directly on the database. You control the exact query.
//    @Query(value = "SELECT * FROM public.users WHERE username = :username", nativeQuery = true)
//    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);


    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndPassword(String username, String password);

//    Optional<User> findByEmailVerificationToken(String token);
//    Optional<User> findByPasswordResetToken(String token);



    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> searchUsers(@Param("search") String search, Pageable pageable);

//    Need to check the query for a certain date format
//    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :date")
//    Long countNewUsersFromDate(@Param("date") Instant date);

    @Query("SELECT COUNT(u) FROM User u WHERE u.status = 'ACTIVE'")
    Long countActiveUsers();

//   Need to check and this
//    @Query("SELECT COUNT(u) FROM User u WHERE u.emailVerified = true")
//    Long countVerifiedUsers();



}
