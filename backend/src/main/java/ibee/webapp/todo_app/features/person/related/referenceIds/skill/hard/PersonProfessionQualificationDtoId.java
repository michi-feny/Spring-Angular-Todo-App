package ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard;

import ibee.webapp.todo_app.dto.base.StringToDtoIdConvertable;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PersonProfessionQualificationDtoId(
    @NotNull
    @Positive
    Long personId,  

    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long professionQualificationId,

    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long educationInstitutionId
) implements StringToDtoIdConvertable
{
    @Override
    public String toString() {
        return personId + "_" + professionQualificationId+"_"+educationInstitutionId;
    }

}
