package dev.eirzarog.synthor.api.entities.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCriteria {

    private String username;
    private String email;

}