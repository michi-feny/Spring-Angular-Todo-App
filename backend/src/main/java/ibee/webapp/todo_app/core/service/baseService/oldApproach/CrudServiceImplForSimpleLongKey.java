package ibee.webapp.todo_app.core.service.baseService.oldApproach;

import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public abstract class CrudServiceImplForSimpleLongKey<DTO, ENTITY, ID>
        implements CrudServiceForSimpleLongKey<DTO, ENTITY, ID> {

     private final MyBaseCrudRepo<ENTITY, ID> repository;
     private final BaseMapper<DTO, ENTITY> mapper;

   

    @Override
    public List<ENTITY> getAll() {
        List<ENTITY> responseList = new ArrayList<>();
        Iterable<ENTITY> fetchedList = repository.findAll();
        fetchedList.forEach(responseList::add);
        return responseList;
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<DTO> getDtoById(ID id) {
        return getById(id).map(mapper::toDto);
    }

    @Override
    public Optional<ENTITY> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public DTO createFromDto(DTO entity) {
        ENTITY entityToSave = mapper.toEntity(entity);
        return mapper.toDto(repository.save(entityToSave));
    }

    @Override
    public DTO updateFromDto(ID id, DTO entity) {
        ENTITY entityToUpdate = mapper.toEntity(entity);
        return mapper.toDto(repository.save(entityToUpdate));
    }

    @Override
    public ENTITY create(ENTITY entity) {
        return repository.save(entity);
    }

    @Override
    public ENTITY update(ID id, ENTITY entity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Resource not found with id: " + id);
        }
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
