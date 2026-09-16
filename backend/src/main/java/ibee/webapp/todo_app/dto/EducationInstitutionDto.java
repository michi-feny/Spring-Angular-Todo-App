package ibee.webapp.todo_app.dto;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;


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
    @Valid 
    @ConvertGroup(from = OnCreate.class, to = Default.class)
    @ConvertGroup(from = OnUpdate.class, to = Default.class)
    AddressDto address

    
) {

}
