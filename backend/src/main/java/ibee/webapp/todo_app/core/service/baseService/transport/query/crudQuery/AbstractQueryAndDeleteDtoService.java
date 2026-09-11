package ibee.webapp.todo_app.core.service.baseService.transport.query.crudQuery;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacadeService;
import ibee.webapp.todo_app.core.service.baseService.transport.QueryAndDeleteDtoService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

public abstract class AbstractQueryAndDeleteDtoService<DTO, ENTITY, ID, IDDTO>
    implements QueryAndDeleteDtoService<DTO, IDDTO> {

    protected final MyCrudBaseEntityFacadeService<ENTITY, ID> entityService;
    protected final BaseMapper<DTO, ENTITY> mapper;
    protected final BaseMapper<IDDTO, ID> idReferenceMapper;

    public AbstractQueryAndDeleteDtoService(
            MyCrudBaseEntityFacadeService<ENTITY, ID> entityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {
        this.entityService = entityService;
        this.mapper = mapper;
        this.idReferenceMapper = idReferenceMapper;
    }

    @Override
    public DTO findById(IDDTO dtoId) {
        return mapper.toDto(entityService.findById(idReferenceMapper.toEntity(dtoId)));
    }

    @Override
    public Optional<DTO> findByIdWithoutException(IDDTO dtoId) {
        return entityService.findByIdWithoutException(idReferenceMapper.toEntity(dtoId))
                .map(mapper::toDto);
    }

    @Override
    public List<DTO> findAll() {
        return mapper.toDtoList(entityService.findAll());
    }

    @Override
    public void deleteById(IDDTO dtoId) {
        entityService.deleteById(idReferenceMapper.toEntity(dtoId));
    }

    @Override
    public void delete(DTO dto) {
        entityService.delete(mapper.toEntity(dto));
    }
}
