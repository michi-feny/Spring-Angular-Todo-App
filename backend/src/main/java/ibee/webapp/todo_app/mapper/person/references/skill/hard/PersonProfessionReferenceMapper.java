package ibee.webapp.todo_app.mapper.person.references.skill.hard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionDtoId;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;

@Mapper(config = MapStructConfig.class)
public interface PersonProfessionReferenceMapper {

    @Mapping(
        target = "professionQualificationId",
        source = "professionQualificationId"
    )
    PersonProfessionDtoId toModel(
            PersonProfessionQualificationId id
    );
}
