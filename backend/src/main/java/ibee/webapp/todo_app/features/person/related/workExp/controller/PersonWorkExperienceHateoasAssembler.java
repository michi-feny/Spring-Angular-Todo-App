package ibee.webapp.todo_app.features.person.related.workExp.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Component;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;

@Component
public class PersonWorkExperienceHateoasAssembler 
        extends AbstractHateoasAssembler<PersonWorkExperienceDto, PersonWorkExperienceId> {

    public PersonWorkExperienceHateoasAssembler() {
        // Bind the precise controller class to enable methodOn() route inference
        super(PersonWorkExperienceController.class);
    }

    @Override
    protected PersonWorkExperienceId extractId(PersonWorkExperienceDto dto) {
        // Maps the composite key for the dynamic link generation
        return new PersonWorkExperienceId(
            dto.id().personId(), 
            dto.id().workExperienceId()
        );
    }

    /**
     * Overrides the base collection model to append the bulk merge link.
     */
    @Override
    public CollectionModel<EntityModel<PersonWorkExperienceDto>> toCollectionModel(
            Iterable<? extends PersonWorkExperienceDto> dtos) {
        
        // 1. Generate the base collection with item models and the "self" list link
        CollectionModel<EntityModel<PersonWorkExperienceDto>> collectionModel = 
                super.toCollectionModel(dtos);

        // 2. Append the custom collection-level "merge" transition
        collectionModel.add(
            linkTo(methodOn(PersonWorkExperienceController.class).mergeWorkExperiences(null, null))
                .withRel("merge")
        );

        return collectionModel;
    }
}
