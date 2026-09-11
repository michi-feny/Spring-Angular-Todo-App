package ibee.webapp.todo_app.dto;

import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record EducationInstitutionDto(

    //has not jet any ID
    //@NotNull
    //@Positive //is implizit handeled by @ValidId
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,

    @NotBlank
    @Size(max = 200)
    String name,

    @NotNull
    AddressDto address

    
) {

}
