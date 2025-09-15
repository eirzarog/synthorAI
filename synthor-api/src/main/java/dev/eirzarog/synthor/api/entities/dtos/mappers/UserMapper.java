package dev.eirzarog.synthor.api.entities.dtos.mappers;

import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.entities.dtos.UserDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO dto);

}