package ibee.webapp.todo_app.mapper.person.skill.soft;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.skills.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.skill.soft.PersonSoftSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.skills.soft.SoftSkillMapper;
@Mapper(
    config = MapStructConfig.class,
    uses = {
        SoftSkillMapper.class,
        PersonSoftSkillReferenceMapper.class    
        
    }
)
public interface PersonSoftSkillMapper 
    extends BaseMapper<PersonSoftSkillDto, PersonSoftSkill> {

        @Override
        @Mapping(target = "id", source = "id")
        @Mapping(target = "softSkill", source = "softSkill")
        @Mapping(target = "person.id", source = "id.personId")
        PersonSoftSkill toEntity(PersonSoftSkillDto dto);   

        @Override
        @Mapping(target = "id", source = "id")
        @Mapping(target = "softSkill", source = "softSkill")  
        PersonSoftSkillDto toDto(PersonSoftSkill entity);

}
