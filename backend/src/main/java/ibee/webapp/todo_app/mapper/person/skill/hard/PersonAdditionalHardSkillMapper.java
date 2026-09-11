package ibee.webapp.todo_app.mapper.person.skill.hard;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonAdditionalHardSkillDto;
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

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "additionalHardSkill", source = "personAdditionalHardSkillDto")
    void updateEntityFromDto(PersonAdditionalHardSkillDto dto, @MappingTarget PersonAdditionalHardSkill entity);

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "additionalHardSkill", source = "additionalHardSkill")
    void updateEntityFromEntity(PersonAdditionalHardSkill sourceUpdates, @MappingTarget PersonAdditionalHardSkill dbEntity);
  
}
