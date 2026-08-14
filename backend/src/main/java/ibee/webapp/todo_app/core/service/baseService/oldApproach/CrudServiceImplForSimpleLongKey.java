package ibee.webapp.todo_app.core.service.baseService.oldApproach;

import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public abstract class CrudServiceImplForSimpleLongKey<T,ID> implements CrudServiceForSimpleLongKey<T, ID> {

    private final MyBaseCrudRepo<T,ID> repository;

   

    @Override
    public List<T> getAll() {
        List<T> responseList = new ArrayList<>();
        Iterable<T> fetchedList = repository.findAll();
        fetchedList.forEach(responseList::add);
        return responseList;
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(ID id, T entity) {
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
