package ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonSoftSkillDtoId(
    @NotNull
    @Positive
    Long personId,
    
    @NotNull
    @Positive
    Long softSkillId
) {

}
