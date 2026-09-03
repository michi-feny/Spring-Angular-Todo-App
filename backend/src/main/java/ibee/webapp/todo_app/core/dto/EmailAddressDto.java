package ibee.webapp.todo_app.core.dto;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailAddressDto(

    @ValidId
    Long id,

    @NotBlank
    @Email
    @Size(max = 320)
    String emailAddress

) {

}
