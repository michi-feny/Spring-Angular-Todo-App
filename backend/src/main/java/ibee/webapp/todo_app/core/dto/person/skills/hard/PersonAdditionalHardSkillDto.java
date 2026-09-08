package ibee.webapp.todo_app.core.dto.person.skills.hard;

import ibee.webapp.todo_app.core.dto.skills.hard.AdditionalHardSkillDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonAdditionalHardSkillDto(

    @ValidId
    PersonAdditionalHardSkillDtoId id,

    
    @NotNull
    AdditionalHardSkillDto personAdditionalHardSkillDto

    //String category :: for later us if needed
    
) {

}
