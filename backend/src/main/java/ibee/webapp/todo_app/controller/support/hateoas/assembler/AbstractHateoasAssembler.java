package ibee.webapp.todo_app.controller.support.hateoas.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractSpringHateoasCrudController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * C is the Concrete Controller Class. This allows methodOn(Controller.class) to 
 * map to the exact @RequestMapping of your specific endpoints.
 */
public abstract class AbstractHateoasAssembler<DTO, ID> 
        implements RepresentationModelAssembler<DTO, EntityModel<DTO>> {

    private final Class<? extends AbstractSpringHateoasCrudController<DTO, ID>> controllerClass;

    protected AbstractHateoasAssembler(Class<? extends AbstractSpringHateoasCrudController<DTO, ID>> controllerClass) {
        this.controllerClass = controllerClass;
    }

    // Forces concrete classes to provide the ID from the DTO
    protected abstract ID extractId(DTO dto);

    @Override
    public EntityModel<DTO> toModel(DTO dto) {
        ID id = extractId(dto);
        
        // WebMvcLinkBuilder dynamically infers the URL, handling composite IDs gracefully!
        return EntityModel.of(dto,
            linkTo(methodOn(controllerClass).getById(null, id)).withSelfRel(),
            linkTo(methodOn(controllerClass).update(null, id, dto)).withRel("update"),
            linkTo(methodOn(controllerClass).delete(null, id)).withRel("delete"),
            linkTo(methodOn(controllerClass).getAll(null)).withRel("list")
        );
    }

    public CollectionModel<EntityModel<DTO>> toCollectionModel(List<DTO> dtos) {
        List<EntityModel<DTO>> entityModels = dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityModels,
            linkTo(methodOn(controllerClass).getAll(null)).withSelfRel()
        );
    }
}
