package ibee.webapp.todo_app.mapper.skills.hard;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.core.dto.skills.hard.AdditionalHardSkillDto;
import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;

@Mapper(
    config = MapStructConfig.class,
    imports = { SkillType.class }
)
public interface AdditionalHardSkillMapper 
    extends BaseMapper<AdditionalHardSkillDto, AdditionalHardSkill> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")  
    @Mapping(target = "level", source = "level")
    //category dosnt get Mapped jet, cause it is not use jet 
    // in the entity:
    // @Mapping(target = "category", source = "category")
    @Mapping(target = "skillType", expression = "java(SkillType.ADDITIONAL_HARD_SKILL)")
    AdditionalHardSkillDto toDto(AdditionalHardSkill entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "level", source = "level")
    //category dosnt get Mapped jet, cause it is not use jet 
    // in the entity:
    // @Mapping(target = "category", source = "category")
    AdditionalHardSkill toEntity(AdditionalHardSkillDto dto);

    /* 
    @Override
    default List<AdditionalHardSkillDto> toDtoList(List<AdditionalHardSkill> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    default List<AdditionalHardSkill> toEntityList(List<AdditionalHardSkillDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).toList();
    }
*/

}
