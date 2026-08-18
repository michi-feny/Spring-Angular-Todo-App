package ibee.webapp.todo_app.controller.person.related.skill.hard.degree;


import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-degrees

Retrieves a HATEOAS collection model of all person degrees in the system inside an ApiSuccessResponse.

Get By ID: GET /api/v1/person-degrees/{id}

Retrieves a single degree entity model by its primary ID, complete with automatic HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-degrees

Accepts a validated PersonDegreeDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-degrees/{id}

Updates an existing degree record matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-degrees/{id}

Deletes the degree record matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-degrees/person/{personId}

Retrieves a collection model of all degree records tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-degrees/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonDegreeDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-degrees/{id}/details

Fetches an extended, detailed view of a specific degree entity model.
*/
@RestController
@RequestMapping("/api/v1/person-degrees")
public class PersonDegreeController extends AbstractSpringPersonRelatedHateoasController<
        PersonDegreeDto,
        PersonDegree,
        PersonDegreeId,
        PersonDegreeDtoId> {

    public PersonDegreeController(
            PersonRelatedDtoService<PersonDegreeDto, PersonDegree, PersonDegreeId, PersonDegreeDtoId> service,
            TranslationService translationService,
            PersonDegreeModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personDegree");
    }
}
