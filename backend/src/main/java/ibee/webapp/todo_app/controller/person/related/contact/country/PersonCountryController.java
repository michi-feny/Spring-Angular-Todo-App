package ibee.webapp.todo_app.controller.person.related.contact.country;

import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.contact.country.PersonCountryDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-countries

Retrieves a HATEOAS collection model of all person countries/nationalities in the system inside an ApiSuccessResponse.

Get By ID: GET /api/v1/person-countries/{id}

Retrieves a single country entity model by its primary ID, complete with automatic HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-countries

Accepts a validated PersonCountryDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-countries/{id}

Updates an existing country record matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-countries/{id}

Deletes the country record matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-countries/person/{personId}

Retrieves a collection model of all country/nationality records tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-countries/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonCountryDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-countries/{id}/details

Fetches an extended, detailed view of a specific country entity model.
*/
@RestController
@RequestMapping("/api/v1/person-countries")
public class PersonCountryController extends AbstractSpringPersonRelatedHateoasController<
        PersonCountryDto,
        PersonCountry,
        PersonCountryId,
        PersonCountryDtoId> {

    public PersonCountryController(
            PersonRelatedDtoService<PersonCountryDto, PersonCountry, PersonCountryId, PersonCountryDtoId> service,
            TranslationService translationService,
            PersonCountryModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personCountry");
    }
}
