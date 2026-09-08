package ibee.webapp.todo_app.features.person.controller;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.features.person.dto.PersonData;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;
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
        EntityModel<PersonData> model = super.toModel(dto);
        Long personId = extractId(dto);

        if (personId != null) {
            model.add(
                linkTo(PersonController.class)
                    .slash(personId)
                    .slash("overview")
                    .withRel("overview")
            );
        }

        String baseUrl = linkTo(PersonController.class).toUri().toString();

        if (dto.firstName() != null && !dto.firstName().isBlank()) {
            String uri = UriComponentsBuilder.fromUriString(baseUrl + "/search/firstName")
                    .queryParam("firstName", dto.firstName())
                    .build()
                    .toUriString();
            model.add(Link.of(uri).withRel("search-first-name"));
        }

        if (dto.lastName() != null && !dto.lastName().isBlank()) {
            String uri = UriComponentsBuilder.fromUriString(baseUrl + "/search/lastName")
                    .queryParam("lastName", dto.lastName())
                    .build()
                    .toUriString();
            model.add(Link.of(uri).withRel("search-last-name"));
        }

        if (dto.birthDate() != null) {
            String uri = UriComponentsBuilder.fromUriString(baseUrl + "/search/birthDate")
                    .queryParam("birthDate", dto.birthDate().toString())
                    .build()
                    .toUriString();
            model.add(Link.of(uri).withRel("search-birth-date"));
        }

        if (dto.socialRecordNumber() != null) {
            String uri = UriComponentsBuilder.fromUriString(baseUrl + "/search/socialRecordNumber")
                    .queryParam("socialRecordNumber", dto.socialRecordNumber())
                    .build()
                    .toUriString();
            model.add(Link.of(uri).withRel("search-social-record-number"));
        }

        return model;
    }
}