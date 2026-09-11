package ibee.webapp.todo_app.core.service.baseService.transport;

import java.util.List;
import java.util.Optional;

public interface PersonRelatedQueryDtoService<DTO, IDDTO> 
    extends QueryAndDeleteDtoService<DTO, IDDTO> {
        
    List<IDDTO> findIdsByPersonId(Long personId);
    List<DTO> findByPersonId(Long personId);
    DTO findWithDetailsById(IDDTO id);

    Optional<DTO> findWithDetailsByIdWithoutException(IDDTO dtoId);
    
}
