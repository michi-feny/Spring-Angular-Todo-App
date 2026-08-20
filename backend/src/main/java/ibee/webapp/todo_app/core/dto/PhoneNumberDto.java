package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneNumberDto(

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
