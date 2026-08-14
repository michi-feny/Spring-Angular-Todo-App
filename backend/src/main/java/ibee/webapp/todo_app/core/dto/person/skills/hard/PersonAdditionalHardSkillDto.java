package ibee.webapp.todo_app.core.dto.person.skills.hard;

import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.core.dto.skills.hard.AdditionalHardSkillDto;
import jakarta.validation.constraints.NotNull;

public record PersonAdditionalHardSkillDto(

    @NotNull
    PersonAdditionalHardSkillDtoId id,

    @NotNull
    AdditionalHardSkillDto personAdditionalHardSkillDtoId

    //String category :: for later us if needed
    
) {

}
