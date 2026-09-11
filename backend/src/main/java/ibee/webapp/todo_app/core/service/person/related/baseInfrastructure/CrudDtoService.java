package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;

import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;

@Validated
public interface CrudDtoService<DTO, ID> {

    DTO create(@Validated(OnCreate.class) DTO dto);

    DTO update(@Validated(OnUpdate.class) DTO dto, ID id);

    List<DTO> saveAll(Iterable<DTO> dtos);

    DTO findById(ID id);
    
    Optional<DTO> findByIdWithoutException(ID id);

    List<DTO> findAllById(Iterable<ID> ids);

    List<DTO> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);

    void delete(DTO dto);

    void deleteAllById(Iterable<ID> ids);

    void deleteAll(Iterable<DTO> dtos);
}

