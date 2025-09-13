package dev.eirzarog.synthor.api.entities.dtos.responses.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {


    private String token;
    // getters and setters

/*
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String email;

    private Set<Role> roles;  // class Role
    */

}
