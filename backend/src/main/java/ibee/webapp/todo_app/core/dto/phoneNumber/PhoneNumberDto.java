package ibee.webapp.todo_app.core.dto.phoneNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhoneNumberDto(

    
    @NotBlank  
    @Size(max = 50)
    String phoneNumber,

    @NotBlank
    @Size(max = 4)
    String countryCode,

    @Size(max = 54)
    String fullnumber

    // TODO: add international CountryCode
) {

}
