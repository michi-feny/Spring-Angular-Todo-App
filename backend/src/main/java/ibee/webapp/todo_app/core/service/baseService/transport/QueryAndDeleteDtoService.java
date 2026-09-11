package ibee.webapp.todo_app.core.service.baseService.transport;

import java.util.List;
import java.util.Optional;

// 1. Base Read & Delete
public interface QueryAndDeleteDtoService<DTO, IDDTO> {
    DTO findById(IDDTO id);
    Optional<DTO> findByIdWithoutException(IDDTO id);
    List<DTO> findAll();
    void deleteById(IDDTO id);
    void delete(DTO dto);
}


