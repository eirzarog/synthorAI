package dev.eirzarog.synthor.api.entities.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data // Full getters, setters, equals/hashCode, toString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Instant createAt;
    private Instant updateAt;






}