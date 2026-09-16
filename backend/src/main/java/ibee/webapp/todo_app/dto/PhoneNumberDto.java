package ibee.webapp.todo_app.dto;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record PhoneNumberDto(

    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,
    
    @NotBlank  
    @Size(max = 50)
    String phoneNumber,

    @NotBlank
    @Size(max = 4)
    String countryCode,

    @Size(max = 54)
    String fullNumber,

    @NotNull
    @Positive 
    Long nationalityId

    // TODO: add international CountryCode
) {

}
