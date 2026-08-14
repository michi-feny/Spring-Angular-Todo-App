package ibee.webapp.todo_app.mapper.baseMaper;

import java.util.List;

/**
 * https://madukajayawardana.medium.com/maximizing-java-code-efficiency-and-clarity-with-mapstruct-a-comprehensive-guide-be3a498c6d74
 * 
 */

public interface BaseMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

    List<DTO> toDtoList(List<ENTITY> entities);

    List<ENTITY> toEntityList(List<DTO> dtos);

   
}
