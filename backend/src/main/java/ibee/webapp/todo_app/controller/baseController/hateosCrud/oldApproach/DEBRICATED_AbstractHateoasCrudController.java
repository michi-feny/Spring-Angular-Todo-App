package ibee.webapp.todo_app.controller.baseController.hateosCrud.oldApproach;


import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.service.baseService.newApproach.CrudDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class DEBRICATED_AbstractHateoasCrudController<DTO, ID> {

    protected final CrudDtoService<DTO, ID> service;
    protected final TranslationService translationService;
    protected final String basePath;
    protected final String entityKey; // Z.B. "entity.person" für die Übersetzung

    protected DEBRICATED_AbstractHateoasCrudController(
            CrudDtoService<DTO, ID> service,
            TranslationService translationService,
            String basePath,
            String entityKey) {
        this.service = service;
        this.translationService = translationService;
        this.basePath = basePath;
        this.entityKey = entityKey;
    }

    /**
     * Hilfsmethode, um den übersetzten Namen der Entity zu holen (z.B. "Person")
     */
    protected String getEntityName() {
        return translationService.translate(entityKey, entityKey);
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<DTO>>> getAll(
            @AuthenticationPrincipal AuthenticatedUser userDetails) {
        
        List<DTO> list = service.findAll();
        List<Link> links = HateoasLinkBuilder.getCollectionLinks(basePath);
        String message = translationService.translate("crud.loadedAll", getEntityName());

        return ResponseEntity.ok(new ApiSuccessResponse<>(list, message, links));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<DTO>> getById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id) {
        
        DTO dto = service.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    translationService.translate("crud.notFound", getEntityName(), id)
                ));

        List<Link> links = HateoasLinkBuilder.getDefaultCrudLinks(basePath, (Long) id);
        String message = translationService.translate("crud.loaded", getEntityName());

        return ResponseEntity.ok(new ApiSuccessResponse<>(dto, message, links));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<DTO>> create(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @Valid @RequestBody DTO dto) {

        DTO created = service.save(dto);
        List<Link> links = HateoasLinkBuilder.getCollectionLinks(basePath);
        String message = translationService.translate("crud.created", getEntityName());

        return new ResponseEntity<>(
            new ApiSuccessResponse<>(created, message, links), 
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<DTO>> update(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id,
            @NotNull @Valid @RequestBody DTO dto) {

        if (!service.existsById(id)) {
            throw new RuntimeException(
                translationService.translate("crud.notFound", getEntityName(), id)
            );
        }

        DTO updated = service.save(dto);
        List<Link> links = HateoasLinkBuilder.getDefaultCrudLinks(basePath, (Long) id);
        String message = translationService.translate("crud.updated", getEntityName());

        return ResponseEntity.ok(new ApiSuccessResponse<>(updated, message, links));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id) {

        if (!service.existsById(id)) {
            throw new RuntimeException(
                translationService.translate("crud.notFound", getEntityName(), id)
            );
        }

        service.deleteById(id);
        List<Link> links = HateoasLinkBuilder.getCollectionLinks(basePath);
        String message = translationService.translate("crud.deleted", getEntityName());

        return ResponseEntity.ok(new ApiSuccessResponse<>(message, links));
    }
}
