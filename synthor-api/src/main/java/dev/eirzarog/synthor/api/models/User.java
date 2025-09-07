package dev.eirzarog.synthor.api.models;

import dev.eirzarog.synthor.api.enums.UserRole;
import dev.eirzarog.synthor.api.enums.UserStatus;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Is it good to have builder with JPA entity for utilizing builder pattern in UserService ?
@Table(name = "users", schema = "public")
public class User extends BaseModel implements UserDetails {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name= "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //@Basic(default) OR @OneToOne in later steps ? (status table)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;

    //@Basic (default) OR @OneToOne in later steps ? (roles table)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private UserRole role = UserRole.USER;


    @Column(name = "email_verified")
    private boolean emailVerified = false;

    public User(String username, String email) {
        this.username = username;
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
