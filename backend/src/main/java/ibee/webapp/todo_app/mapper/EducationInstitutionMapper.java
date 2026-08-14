package ibee.webapp.todo_app.mapper;

/**
 * EducationInstitutionMapper
 */
import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.EducationInstitutionDto;
import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        AddressMapper.class
    }
)
public interface EducationInstitutionMapper
        extends BaseMapper<EducationInstitutionDto, EducationInstitution> {
}
