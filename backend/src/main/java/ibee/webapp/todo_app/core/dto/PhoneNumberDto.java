package ibee.webapp.todo_app.core.dto;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneNumberDto(

    @ValidId
    Long id,
    
    @NotBlank  
    @Size(max = 50)
    String phoneNumber,

    @NotBlank
    @Size(max = 4)
    String countryCode,

    @Size(max = 54)
    String fullNumber

    // TODO: add international CountryCode
) {

}
