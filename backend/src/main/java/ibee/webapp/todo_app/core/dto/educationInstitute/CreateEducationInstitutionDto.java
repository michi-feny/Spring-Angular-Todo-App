package ibee.webapp.todo_app.core.dto.educationInstitute;

import ibee.webapp.todo_app.core.dto.address.CreateAddressDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEducationInstitutionDto(

    //has not jet any ID

    @NotBlank
    @Size(max = 200)
    String name,

    @NotNull
    CreateAddressDto createAddressDto

    
) {

}
