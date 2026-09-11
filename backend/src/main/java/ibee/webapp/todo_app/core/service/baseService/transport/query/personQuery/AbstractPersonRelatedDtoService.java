package ibee.webapp.todo_app.core.service.baseService.transport.query.personQuery;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.transport.PersonRelatedQueryDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.query.crudQuery.AbstractQueryAndDeleteDtoService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

public abstract class AbstractPersonRelatedDtoService<
        DTO,
        ENTITY extends PersonRelatedEntity<ID>,
        ID,
        IDDTO>
    extends AbstractQueryAndDeleteDtoService<DTO, ENTITY, ID, IDDTO>
    implements PersonRelatedQueryDtoService<DTO, IDDTO> {

    protected final PersonRelatedService<ENTITY, ID> personEntityService;

    public AbstractPersonRelatedDtoService(
            PersonRelatedService<ENTITY, ID> personEntityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.personEntityService = personEntityService;
    }

    @Override
    public List<IDDTO> findIdsByPersonId(Long personId) {
        return idReferenceMapper.toDtoList(personEntityService.findIdsByPersonId(personId));
    }

    @Override
    public List<DTO> findByPersonId(Long personId) {
        return mapper.toDtoList(personEntityService.findByPersonId(personId));
    }

    @Override
    public DTO findWithDetailsById(IDDTO dtoId) {
        var entity = personEntityService.findWithDetailsById(idReferenceMapper.toEntity(dtoId));
        var dto = mapper.toDto(entity);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DTO> findWithDetailsByIdWithoutException(IDDTO dtoId) {
        ID entityId = idReferenceMapper.toEntity(dtoId);
        
        // Calls the entity service's safe method and maps it if present
        return personEntityService.findWithDetailsByIdWithoutException(entityId)
                                  .map(mapper::toDto);
    }
}
