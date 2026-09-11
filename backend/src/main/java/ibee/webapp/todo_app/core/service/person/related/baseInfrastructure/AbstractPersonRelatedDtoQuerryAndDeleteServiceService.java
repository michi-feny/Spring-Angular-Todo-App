package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.transport.PersonRelatedQueryDtoService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;


public abstract class AbstractPersonRelatedDtoQuerryAndDeleteServiceService<
        DTO,
        ENTITY extends PersonRelatedEntity<ID>,
        ID,
        IDDTO>
    implements PersonRelatedQueryDtoService<DTO, IDDTO> {

    protected final PersonRelatedService<ENTITY, ID> personEntityService;
    protected final BaseMapper<IDDTO, ID> idReferenceMapper;
    protected final BaseMapper<DTO, ENTITY> mapper;

    public AbstractPersonRelatedDtoQuerryAndDeleteServiceService(
            PersonRelatedService<ENTITY, ID> personEntityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {

        this.personEntityService = personEntityService;
        this.idReferenceMapper = idReferenceMapper;
        this.mapper = mapper;
    }

    @Override
    public List<IDDTO> findIdsByPersonId(Long personId) {
        List<ID> ids = personEntityService
            .findIdsByPersonId(personId);
        return idReferenceMapper.toDtoList(ids);
    }
    
    
    @Override 
    public List<DTO> findByPersonId(Long personId) {
        List<ENTITY> entities = personEntityService
            .findByPersonId(personId);
        return mapper.toDtoList(entities);
    }

    

    @Override
    public Optional<DTO> findWithDetailsByIdWithoutException(IDDTO dtoId) {
        ID entityId = idReferenceMapper.toEntity(dtoId);
        return personEntityService.findWithDetailsByIdWithoutException(entityId)
                                  .map(mapper::toDto);
    }

    @Override
    public DTO findWithDetailsById(IDDTO dtoId) {
        ID entityId = idReferenceMapper.toEntity(dtoId);
        
        // No Optional unwrapping here! 
        // If it's missing, personEntityService throws the actionEvent Exception.
        ENTITY entity = personEntityService.findWithDetailsById(entityId);
        
        return mapper.toDto(entity);
    }

    @Override
    public DTO findById(IDDTO dtoId) {
        ID entityId = idReferenceMapper.toEntity(dtoId);
        ENTITY entity = personEntityService.findById(entityId);
        return mapper.toDto(entity);
    }

    @Override
    public Optional<DTO> findByIdWithoutException(IDDTO dtoId) {
        return personEntityService.findByIdWithoutException(idReferenceMapper.toEntity(dtoId)).map(mapper::toDto);
    }

    @Override
    public List<DTO> findAll() {
        return mapper.toDtoList(personEntityService.findAll());
    }

    @Override
    public void deleteById(IDDTO dtoId) {
        personEntityService.deleteById(idReferenceMapper.toEntity(dtoId));
    }

    @Override
    public void delete(DTO dto) {
        personEntityService.delete(mapper.toEntity(dto));
    }

    
    

   
    

    
    

   

   
}
