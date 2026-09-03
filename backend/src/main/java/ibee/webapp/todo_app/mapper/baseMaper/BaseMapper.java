package ibee.webapp.todo_app.mapper.baseMaper;

import java.util.List;

import org.mapstruct.MappingTarget;

/**
 * https://madukajayawardana.medium.com/maximizing-java-code-efficiency-and-clarity-with-mapstruct-a-comprehensive-guide-be3a498c6d74
 * 
 */

public interface BaseMapper<DTO, ENTITY> 
    extends EntityUpdateMapper<ENTITY>{

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    List<DTO> toDtoList(Iterable<ENTITY> entities);

    List<ENTITY> toEntityList(Iterable<DTO> dtos);

    // Standard UI update (will map explicit nulls if the user clears a field in the UI)
    void updateEntityFromDto(DTO dto, @MappingTarget ENTITY entity);
   
}
