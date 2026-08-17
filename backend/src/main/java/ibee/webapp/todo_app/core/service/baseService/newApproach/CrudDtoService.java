package ibee.webapp.todo_app.core.service.baseService.newApproach;

import java.util.List;
import java.util.Optional;

public interface CrudDtoService<DTO, ID> {

    DTO save(DTO dto);

    List<DTO> saveAll(Iterable<DTO> dtos);

    Optional<DTO> findById(ID id);

    List<DTO> findAllById(Iterable<ID> ids);

    List<DTO> findAll();

    boolean existsById(ID id);

    void deleteById(ID id);

    void delete(DTO dto);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends DTO> dtos);
}
