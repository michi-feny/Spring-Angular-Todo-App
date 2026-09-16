package ibee.webapp.todo_app.core.service.baseService.transport;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.repository.baseRepo.IdentifiableEntity;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacadeService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.CrudDtoService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;


@Transactional
public abstract class AbstractMappedCrudDtoService
    <DTO, ENTITY extends IdentifiableEntity<ID>, ID, IDDTO>
        implements CrudDtoService<DTO, IDDTO> {

    protected final MyCrudBaseEntityFacadeService<ENTITY, ID> entityService;
    protected final BaseMapper<DTO, ENTITY> mapper;
    protected final BaseMapper<IDDTO, ID> idReferenceMapper;

    public AbstractMappedCrudDtoService(
            MyCrudBaseEntityFacadeService<ENTITY, ID> entityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {
        
        this.entityService = entityService;
        this.mapper = mapper;
        this.idReferenceMapper = idReferenceMapper;
    }

    public AbstractMappedCrudDtoService(
            MyCrudBaseEntityFacadeService<ENTITY, ID> service, 
            BaseMapper<DTO, ENTITY> mapper) {
        
        this.entityService = service;
        this.mapper = mapper;
        this.idReferenceMapper = null;
        
        // If your base class requires an idReferenceMapper, 
        // you can safely set it to null here, or assign a simple pass-through lambda 
        // depending on how your base class is structured!
    }


    @Override
    public DTO create(DTO dto) {
        ENTITY e = mapper.toEntity(dto);
        ENTITY result = entityService.create(e);
        return 
            mapper.toDto(result)
        ;
    }

    @Override
    public DTO update(DTO dto, IDDTO idDto){
        ID id = toDbId(idDto);
        ENTITY entity = mapper.toEntity(dto);

        ENTITY result = entityService
            .update(entity,id);
        
        return 
            mapper.toDto(result)
        ;
    }

    @Override
    public List<DTO> saveAll(Iterable<DTO> dtos) {
        List<ENTITY> entities = mapper.toEntityList(dtos);
        List<ENTITY> saved = entityService.saveAll(entities);
        return mapper.toDtoList(saved);
    }

    @Override
    public DTO findById(IDDTO idDto) {
        ID id = toDbId(idDto);
        return mapper.toDto(entityService.findById(id));
    }

    @Override
    public Optional<DTO> findByIdWithoutException(IDDTO idDto) {
        ID id = toDbId(idDto);
        return entityService.findByIdWithoutException(id).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DTO> findAllById(Iterable<IDDTO> idDtos) {
        List<ID> ids = toDbIdList(idDtos);
        return mapper.toDtoList(entityService.findAllById(ids));
    }

    @Override
    public List<DTO> findAll() {
        return mapper.toDtoList(entityService.findAll());
    }

    @Override
    public boolean existsById(IDDTO idDto) {
        ID id = toDbId(idDto);
        return entityService.existsById(id);
    }

    @Override
    public void deleteById(IDDTO idDto) {
        ID id = toDbId(idDto);
        entityService.deleteById(id);
    }

    @Override
    public void delete(DTO dto) {
        ENTITY e = mapper.toEntity(dto);
        entityService.delete(e);
    }

    @Override
    public void deleteAllById(Iterable<IDDTO> idDtos) {
        List<ID> ids = toDbIdList(idDtos);
        entityService.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<DTO> dtos) {
        List<DTO> dtoList = StreamSupport.stream(dtos.spliterator(), false)
                .map(dto -> (DTO) dto)
                .toList();
        List<ENTITY> entities = mapper.toEntityList(dtoList);
        entityService.deleteAll(entities);
    }

    // =========================================================================
    // SMART ID MAPPING HELPERS (Protects against NullPointerException)
    // =========================================================================

    @SuppressWarnings("unchecked")
    protected ID toDbId(IDDTO idDto) {
        if (idReferenceMapper != null) {
            return idReferenceMapper.toEntity(idDto); // Translate composite IDs
        }
        // If mapper is null, we assume ID and IDDTO are the exact same type (e.g., Long)
        return (ID) idDto; 
    }

    @SuppressWarnings("unchecked")
    protected List<ID> toDbIdList(Iterable<IDDTO> idDtos) {
        if (idReferenceMapper != null) {
            return idReferenceMapper.toEntityList(idDtos); // Translate composite ID lists
        }
        // Safely cast the whole list
        return StreamSupport.stream(idDtos.spliterator(), false)
                .map(id -> (ID) id)
                .toList();
    }

 
    
}
