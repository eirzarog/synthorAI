package dev.eirzarog.synthor.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.eirzarog.synthor.api.entities.base.BaseEntity;
import dev.eirzarog.synthor.api.enumerators.UserRole;
import dev.eirzarog.synthor.api.enumerators.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// CIRCULAR REFERENCE IN ENTITIES WITH @Data
// PROBLEMATIC: This can cause infinite loops in toString(), equals(), hashCode()
// DON'T USE @Data with bidirectional relationships

// Bidirectional Relationships: Proper JPA relationships with helper methods

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "TEXT")
    private String lastName;

    @Column(name= "username", unique = true, nullable = false, columnDefinition = "TEXT")
    private String username;

    @Column(name = "email", unique = true, columnDefinition = "TEXT")
    private String email;

    @Column(name = "display_name", columnDefinition = "TEXT")
    private String display_name;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "TEXT")
    private UserStatus status = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "TEXT")
    private UserRole role = UserRole.USER;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "remember_me")
    private boolean rememberMe = false;

    //Prevents conflicting profile updates
    @Version
    @Column(name = "version")
    private int version = 0;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of();
    }
    @Override
    public boolean isAccountNonExpired() {
        return status != UserStatus.EXPIRED;
    }
    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.LOCKED;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return status != UserStatus.CREDENTIALS_EXPIRED;
    }
    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}
