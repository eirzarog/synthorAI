package dev.eirzarog.synthor.api.entities.dtos.mappers;

import dev.eirzarog.synthor.api.entities.Chat;
import dev.eirzarog.synthor.api.entities.dtos.ChatDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatDTO toDto(Chat chat);
    Chat toEntity(ChatDTO dto);
}