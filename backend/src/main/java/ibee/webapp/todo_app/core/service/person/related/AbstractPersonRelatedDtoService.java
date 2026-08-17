package ibee.webapp.todo_app.core.service.person.related;

import java.util.List;
import java.util.Optional;

import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.baseService.newApproach.AbstractCrudDtoService;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;


public abstract class AbstractPersonRelatedDtoService<
        DTO,
        ENTITY extends PersonRelatedEntity,
        ID,
        IDDTO>
    extends AbstractCrudDtoService<DTO, ENTITY, ID> {

    protected final PersonRelatedService<ENTITY, ID> personEntityService;
    protected final BaseMapper<IDDTO, ID> idReferenceMapper;

    public AbstractPersonRelatedDtoService(
            PersonRelatedService<ENTITY, ID> personEntityService,
            BaseMapper<DTO, ENTITY> mapper,
            BaseMapper<IDDTO, ID> idReferenceMapper) {
        super(personEntityService, mapper);
        this.personEntityService = personEntityService;
        this.idReferenceMapper = idReferenceMapper;
    }

    public List<IDDTO> findIdsByPersonId(Long personId) {
        List<ID> ids = personEntityService.findIdsByPersonId(personId);
        return idReferenceMapper.toDtoList(ids);
    }

    public List<DTO> findByPersonId(Long personId) {
        return mapper.toDtoList(personEntityService.findByPersonId(personId));
    }

    public Optional<DTO> findWithDetailsById(ID id) {
        return personEntityService.findWithDetailsById(id).map(mapper::toDto);
    }

   
}
