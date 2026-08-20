package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EducationInstitutionDto(

    //has not jet any ID
    @NotNull
    @Positive
    Long id,

    @NotBlank
    @Size(max = 200)
    String name,

    @NotNull
    AddressDto address

    
) {

}
