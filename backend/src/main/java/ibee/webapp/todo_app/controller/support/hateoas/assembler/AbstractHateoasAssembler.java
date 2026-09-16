package ibee.webapp.todo_app.controller.support.hateoas.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * C is the Concrete Controller Class. This allows methodOn(Controller.class) to 
 * map to the exact @RequestMapping of your specific endpoints.
 */
public abstract class AbstractHateoasAssembler<DTO, ID> 
        implements RepresentationModelAssembler<DTO, EntityModel<DTO>> {

    private final Class<?> controllerClass;

    protected AbstractHateoasAssembler(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    protected Class<?> getController(){
        return controllerClass;
    }

    protected abstract ID extractId(DTO dto);

    /** Override this to return false if the controller does NOT have a GET / endpoint */
    protected boolean hasListEndpoint() {
        return true; 
    }

    /** Override this to return false if the controller does NOT have a GET /{id} endpoint */
    protected boolean hasSingleItemEndpoint() {
        return true; 
    }

    //TODO adapt the toModel in evry assembler, so it is showing the correct links..
    //add also the corrrect return type false, for all theat dosnt have that list fuctionality
    //override: hasListEndpoint to return false
    // @Override
    // public EntityModel<DTO> toModel(DTO dto) {
    //     ID id = extractId(dto);
    //     List<Link> links = new ArrayList<>();

    //     if (id != null) {
    //         links.add(linkTo(controllerClass).slash(id).withSelfRel());
    //         links.add(linkTo(controllerClass).slash(id).withRel("update"));
    //         links.add(linkTo(controllerClass).slash(id).withRel("delete"));
    //     }
        
    //     links.add(linkTo(controllerClass).withRel("list"));

    //     return EntityModel.of(dto, links);
    // }
    @Override
    public EntityModel<DTO> toModel(DTO dto) {
        ID id = extractId(dto);
        List<Link> links = new ArrayList<>();

        if (id != null) {
            if (hasSingleItemEndpoint()) {
                links.add(linkTo(controllerClass).slash(id).withSelfRel());
            }
            links.add(linkTo(controllerClass).slash(id).withRel("update"));
            links.add(linkTo(controllerClass).slash(id).withRel("delete"));
        }
        
        // Wrap the list link in the toggle!
        if (hasListEndpoint()) {
            links.add(linkTo(controllerClass).withRel("list"));
        }

        return EntityModel.of(dto, links);
    }

    public CollectionModel<EntityModel<DTO>> toCollectionModel(Iterable<? extends DTO> dtos) {
        List<EntityModel<DTO>> entityModels = StreamSupport.stream(dtos.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(entityModels,
            linkTo(controllerClass).withSelfRel()
        );
    }

    
}