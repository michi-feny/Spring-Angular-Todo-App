package ibee.webapp.todo_app.core.dto;

import ibee.webapp.todo_app.validation.idHandle.ValidId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record AddressDto(

    //@NotNull
   // @Positive is implizit handeled by @ValidId
    //@ValidId
    @NotNull(groups = OnUpdate.class)
    @Positive(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    Long id,

    @NotBlank
    @Size( max = 200)
    String street,

    @NotBlank
    @Size(max = 20)
    String houseNumber,

    @NotBlank
    @Size( max = 20)
    String zipCode,

    @NotBlank
    @Size(max =100)
    String city,

    @NotNull
    Long nationalityId

    //@NotNull
    //CountryDto country

) {
}
