package ibee.webapp.todo_app.core.service.baseService.newApproach;

import org.springframework.transaction.annotation.Transactional;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

import java.util.List;
import java.util.Optional;


@Transactional
public abstract class BaseCrudServiceImpl<ENTITY, ID>
        implements BaseCrudService<ENTITY, ID> {

    protected final MyBaseCrudRepo<ENTITY, ID> repository;

    protected BaseCrudServiceImpl(
            MyBaseCrudRepo<ENTITY, ID> repository) {

        this.repository = repository;
    }

    @Override
    public ENTITY save(ENTITY entity) {
        return repository.save(entity);
    }

    @Override
    public List<ENTITY> saveAll(
            Iterable<ENTITY> entities) {

        return repository.saveAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ENTITY> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAllById(
            Iterable<ID> ids) {

        return repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ENTITY> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(ENTITY entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAllById(
            Iterable<? extends ID> ids) {

        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(
            Iterable<? extends ENTITY> entities) {

        repository.deleteAll(entities);
    }
}
