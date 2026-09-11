package ibee.webapp.todo_app.features.person.related.skill.dto.hard;

import ibee.webapp.todo_app.dto.skills.hard.AdditionalHardSkillDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonAdditionalHardSkillDto(

    @Valid 
    PersonAdditionalHardSkillDtoId id,

    
    @NotNull
    AdditionalHardSkillDto personAdditionalHardSkillDto

    //String category :: for later us if needed
    
) {

}
