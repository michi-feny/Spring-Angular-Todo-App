package ibee.webapp.todo_app.controller.person;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.PersonData;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PersonModelAssembler 
        extends AbstractHateoasAssembler<PersonData, Long> {

    public PersonModelAssembler() {
        super(PersonController.class);
    }

    @Override
    protected Long extractId(PersonData dto) {
        return dto.id(); 
    }

    @Override
    public EntityModel<PersonData> toModel(PersonData dto) {
        // 1. Get standard CRUD links (self, update, delete, list)
        EntityModel<PersonData> model = super.toModel(dto);
        Long personId = extractId(dto);

        // 2. Add Link to the Overview Endpoint
        model.add(
            linkTo(methodOn(PersonController.class).getOverview(null, personId))
            .withRel("overview")
        );

        // 3. Add Links for Single-Field Searches (Only if the data exists)
        if (dto.firstName() != null && !dto.firstName().isBlank()) {
            model.add(
                linkTo(methodOn(PersonController.class).searchByFirstName(null, dto.firstName()))
                .withRel("search-first-name")
            );
        }

        if (dto.lastName() != null && !dto.lastName().isBlank()) {
            model.add(
                linkTo(methodOn(PersonController.class).searchByLastName(null, dto.lastName()))
                .withRel("search-last-name")
            );
        }

        if (dto.birthDate() != null) {
            model.add(
                linkTo(methodOn(PersonController.class).searchByBirthDate(null, dto.birthDate()))
                .withRel("search-birth-date")
            );
        }

        if (dto.socialRecordNumber() != null) {
            model.add(
                linkTo(methodOn(PersonController.class).searchBySocialRecordNumber(null, dto.socialRecordNumber()))
                .withRel("search-social-record-number")
            );
        }

        return model;
    }
}
