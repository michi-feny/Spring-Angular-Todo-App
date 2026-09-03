package ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft;


import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonSoftSkillDtoId(
    @NotNull
    @Positive
    Long personId,
    
    @NotNull
    @Positive
    Long softSkillId
) implements StringToDtoIdConvertable
{

}
