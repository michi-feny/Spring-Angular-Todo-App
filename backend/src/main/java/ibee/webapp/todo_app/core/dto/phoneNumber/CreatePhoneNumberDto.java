package ibee.webapp.todo_app.core.dto.phoneNumber;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePhoneNumberDto(
    
    Long id,

    PhoneNumberDto createdNumberDto
) {

}
