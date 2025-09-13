package dev.eirzarog.synthor.api.entities.dtos.mappers;

import dev.eirzarog.synthor.api.entities.Message;
import dev.eirzarog.synthor.api.entities.dtos.MessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDTO toDto(Message message);
    Message toEntity(MessageDTO dto);
}