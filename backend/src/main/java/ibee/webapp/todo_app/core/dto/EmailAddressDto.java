package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailAddressDto(

    Long id,

    @NotBlank
    @Email
    @Size(max = 320)
    String emailAddress

) {

}
