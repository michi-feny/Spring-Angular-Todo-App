package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.transport.AbstractMappedCrudDtoService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

public abstract class AbstractMappedPersonRelatedDtoService<
        DTO,
        ENTITY extends PersonRelatedEntity<ID>,
        ID,
        IDDTO> 
   extends AbstractMappedCrudDtoService<DTO, ENTITY, ID, IDDTO>
   implements PersonRelatedDtoCrudService<DTO, IDDTO> {

    protected final PersonRelatedService<ENTITY, ID> personEntityService;
    private final BaseMapper<IDDTO, ID> idReferenceMapper;
    private final BaseMapper<DTO, ENTITY> mapper;

    public AbstractMappedPersonRelatedDtoService(
            PersonRelatedService<ENTITY, ID> personEntityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {
        
        // Feed the dependencies to the generic CRUD translation class
        super(personEntityService, mapper, idReferenceMapper);
        this.personEntityService = personEntityService;
        this.idReferenceMapper = idReferenceMapper;
        this.mapper = mapper;
    }

    // ==========================================
    // Person-Specific Queries Only!
    // ==========================================

    @Transactional(readOnly = true)
    @Override
    public List<IDDTO> findIdsByPersonId(Long personId) {
        return idReferenceMapper.toDtoList(personEntityService.findIdsByPersonId(personId));
    }
    
    @Transactional(readOnly = true)
    @Override 
    public List<DTO> findByPersonId(Long personId) {
        return mapper.toDtoList(personEntityService.findByPersonId(personId));
    }

    @Transactional(readOnly = true)
    @Override
    public DTO findWithDetailsById(IDDTO dtoId) {
        var entity = personEntityService.findWithDetailsById(idReferenceMapper.toEntity(dtoId));
        var dto = mapper.toDto(entity);
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> findWithDetailsByIdWithoutException(IDDTO dtoId) {
        ID entityId = idReferenceMapper.toEntity(dtoId);
        return personEntityService.findWithDetailsByIdWithoutException(entityId)
                                  .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public DTO findById(IDDTO dtoId) {
        return findWithDetailsById(dtoId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> findByIdWithoutException(IDDTO dtoId) {
        return findWithDetailsByIdWithoutException(dtoId);
    }
}