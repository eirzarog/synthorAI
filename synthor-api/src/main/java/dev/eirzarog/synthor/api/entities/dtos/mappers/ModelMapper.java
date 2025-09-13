package dev.eirzarog.synthor.api.entities.dtos.mappers;

import dev.eirzarog.synthor.api.entities.Model;
import dev.eirzarog.synthor.api.entities.dtos.ModelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    ModelDTO toDto(Model model);
    Model toEntity(ModelDTO dto);
}