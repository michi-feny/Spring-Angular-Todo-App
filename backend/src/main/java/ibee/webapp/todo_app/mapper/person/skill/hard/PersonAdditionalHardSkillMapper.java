package ibee.webapp.todo_app.mapper.person.skill.hard;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonAdditionalHardSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.skills.hard.AdditionalHardSkillMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = { 
        AdditionalHardSkillMapper.class,
        PersonAdditionalHardSkillReferenceMapper.class
     }
)
public interface PersonAdditionalHardSkillMapper
    extends BaseMapper<PersonAdditionalHardSkillDto, PersonAdditionalHardSkill> {

    // DTO <- Entity
    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "personAdditionalHardSkillDto", source = "additionalHardSkill")
    PersonAdditionalHardSkillDto toDto(PersonAdditionalHardSkill entity);

    // Entity <- DTO
    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "additionalHardSkill", source = "personAdditionalHardSkillDto")
    PersonAdditionalHardSkill toEntity(PersonAdditionalHardSkillDto dto);

    // Null-safe list wrappers delegating to element methods
 /*   @Override
    default List<PersonAdditionalHardSkillDto> toDtoList(List<PersonAdditionalHardSkill> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    default List<PersonAdditionalHardSkill> toEntityList(List<PersonAdditionalHardSkillDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).toList();
    }

    // convenience: map embedded id <-> dto id record
    @Mapping(target = "additionalHardSkillId", source = "additionalHardSkillId")
    @Mapping(target = "personId", source = "personId")
    PersonAdditionalHardSkillDtoId toIdDto(PersonAdditionalHardSkillId id);

    @Mapping(target = "additionalHardSkillId", source = "additionalHardSkillId")
    @Mapping(target = "personId", source = "personId")
    PersonAdditionalHardSkillId toIdEntity(PersonAdditionalHardSkillDtoId id);

*/ 
}
