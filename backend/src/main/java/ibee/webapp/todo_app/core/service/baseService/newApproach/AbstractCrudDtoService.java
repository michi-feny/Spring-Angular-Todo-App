package ibee.webapp.todo_app.core.service.baseService.newApproach;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCrudDtoService<DTO, ENTITY, ID>
        implements CrudDtoService<DTO, ID> {

    protected final BaseCrudService<ENTITY, ID> entityService;
    protected final BaseMapper<DTO, ENTITY> mapper;

    @Override
    public DTO save(DTO dto) {
        ENTITY e = mapper.toEntity(dto);
        ENTITY saved = entityService.save(e);
        return mapper.toDto(saved);
    }

    @Override
    public List<DTO> saveAll(Iterable<DTO> dtos) {
        List<ENTITY> entities = mapper.toEntityList(dtos);
        List<ENTITY> saved = entityService.saveAll(entities);
        return mapper.toDtoList(saved);
    }

    @Override
    public Optional<DTO> findById(ID id) {
        return entityService.findById(id).map(mapper::toDto);
    }

    @Override
    public List<DTO> findAllById(Iterable<ID> ids) {
        return mapper.toDtoList(entityService.findAllById(ids));
    }

    @Override
    public List<DTO> findAll() {
        return mapper.toDtoList(entityService.findAll());
    }

    @Override
    public boolean existsById(ID id) {
        return entityService.existsById(id);
    }

    @Override
    public void deleteById(ID id) {
        entityService.deleteById(id);
    }

    @Override
    public void delete(DTO dto) {
        ENTITY e = mapper.toEntity(dto);
        entityService.delete(e);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        entityService.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends DTO> dtos) {
        List<DTO> dtoList = StreamSupport.stream(dtos.spliterator(), false)
                .map(dto -> (DTO) dto)
                .toList();
        List<ENTITY> entities = mapper.toEntityList(dtoList);
        entityService.deleteAll(entities);
    }

   /*  protected ENTITY saveEntity(ENTITY entity) { return entityService.save(entity); }
    protected Optional<ENTITY> findEntityById(ID id) { return entityService.findById(id); }
    protected List<ENTITY> findEntitiesById(Iterable<ID> ids) { return entityService.findAllById(ids); }
    protected List<ENTITY> findAllEntities() { return entityService.findAll(); }
    protected void deleteEntity(ENTITY entity) { entityService.delete(entity); }
*/
    
}
