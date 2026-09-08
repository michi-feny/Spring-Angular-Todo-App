package ibee.webapp.todo_app.core.dto;

import ibee.webapp.todo_app.validation.idHandle.ValidId;


public record CompanyDto(
    @ValidId
    Long id,
    String name,
    String legalForm,
    AddressDto address
) {

}
