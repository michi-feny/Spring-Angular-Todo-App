package ibee.webapp.todo_app.controller.person;

import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractSpringHateoasCrudController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.dto.person.PersonOverview;
import ibee.webapp.todo_app.core.service.person.PersonDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
/*
Because your PersonController is annotated with @RequestMapping("/api/v1/persons") and 
extends AbstractSpringHateoasCrudController<PersonData, Long>, 
it automatically inherits several fully implemented standard CRUD endpoints.  
Here are all the possible endpoint routes that are automatically exposed by the base controller, 
mapped specifically to your PersonData entity:Inherited Standard CRUD Endpoints
GET All PersonsEndpoint: 

GET /api/v1/persons  
Purpose: Fetches a list of all available persons.  
Returns: ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>>.  

GET Person by IDEndpoint: GET /api/v1/persons/{id}  
Purpose: Fetches a single person matching the provided ID path variable.  
Returns: ResponseEntity<ApiSuccessResponse<EntityModel<PersonData>>>.  

CREATE PersonEndpoint: 
POST /api/v1/persons  
Purpose: Creates a new person. It expects a valid PersonData JSON object in the request body.  
Returns: ResponseEntity<ApiSuccessResponse<EntityModel<PersonData>>> with an HTTP status of 201 CREATED.  

UPDATE PersonEndpoint: 
PUT /api/v1/persons/{id}  
Purpose: Fully updates an existing person by their ID. It expects a valid PersonData JSON object in the request body.  
Returns: ResponseEntity<ApiSuccessResponse<EntityModel<PersonData>>>.  

DELETE PersonEndpoint: 
DELETE /api/v1/persons/{id}  
Purpose: Deletes the person matching the provided ID.  
Returns: ResponseEntity<ApiSuccessResponse<Void>> with an HTTP status of 200 OK.  
*/
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController extends AbstractSpringHateoasCrudController<PersonData, Long> {

    private final PersonDtoService personDtoService;

    public PersonController(
            PersonDtoService personDtoService,
            TranslationService translationService,
            PersonModelAssembler assembler) {
        
        super(personDtoService, translationService, assembler, "entity.person");
        this.personDtoService = personDtoService;
    }

    // --- Overview Aggregation ---

    @GetMapping("/{id}/overview")
    public ResponseEntity<ApiSuccessResponse<PersonOverview>> getOverview(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable Long id) {
        
        PersonOverview overview = personDtoService.getOverviewById(id)
                .orElseThrow(() -> new RuntimeException(
                    translationService.translate("crud.notFound", getEntityName(), id.toString())
                ));

        String message = translationService.translate("crud.loadedOverview", getEntityName());
        return buildResponse(overview, message);
    }

    // --- Specific Single-Field Searches ---

    @GetMapping("/search/firstName")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> searchByFirstName(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @RequestParam String firstName) {

        List<PersonData> list = personDtoService.searchByFirstName(firstName);
        CollectionModel<EntityModel<PersonData>> collectionModel = assembler.toCollectionModel(list);
        
        String message = translationService.translate("crud.searchCompleted", getEntityName());
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/search/lastName")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> searchByLastName(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @RequestParam String lastName) {

        List<PersonData> list = personDtoService.searchByLastName(lastName);
        CollectionModel<EntityModel<PersonData>> collectionModel = assembler.toCollectionModel(list);
        
        String message = translationService.translate("crud.searchCompleted", getEntityName());
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/search/birthDate")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> searchByBirthDate(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {

        List<PersonData> list = personDtoService.searchByBirthDate(birthDate);
        CollectionModel<EntityModel<PersonData>> collectionModel = assembler.toCollectionModel(list);
        
        String message = translationService.translate("crud.searchCompleted", getEntityName());
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/search/socialRecordNumber")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> searchBySocialRecordNumber(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @RequestParam Short socialRecordNumber) {

        List<PersonData> list = personDtoService.searchBySocialRecordNumber(socialRecordNumber);
        CollectionModel<EntityModel<PersonData>> collectionModel = assembler.toCollectionModel(list);
        
        String message = translationService.translate("crud.searchCompleted", getEntityName());
        return buildResponse(collectionModel, message);
    }

    // --- Combined Filter Search ---

    @GetMapping("/search")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonData>>>> searchByFilter(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false) Short socialRecordNumber) {

        List<PersonData> list = personDtoService.searchByFilter(firstName, lastName, birthDate, socialRecordNumber);
        CollectionModel<EntityModel<PersonData>> collectionModel = assembler.toCollectionModel(list);
        
        String message = translationService.translate("crud.searchCompleted", getEntityName());
        return buildResponse(collectionModel, message);
    }
}
