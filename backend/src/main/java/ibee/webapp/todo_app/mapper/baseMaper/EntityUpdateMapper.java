package ibee.webapp.todo_app.mapper.baseMaper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface EntityUpdateMapper<ENTITY> {

    // Safely merges a detached entity into an attached DB entity without nullifying missing fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromEntity(ENTITY sourceUpdates, @MappingTarget ENTITY dbEntity);
}
