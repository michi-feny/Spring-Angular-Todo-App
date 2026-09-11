package ibee.webapp.todo_app.core.service.person.related.baseInfrastructure;

import ibee.webapp.todo_app.core.service.baseService.transport.PersonRelatedQueryDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.StandardWriteDtoService;

public interface PersonRelatedDtoCrudService 
    <DTO, IDDTO> 
    extends PersonRelatedQueryDtoService<DTO, IDDTO>, 
            StandardWriteDtoService<DTO, IDDTO>
{

}
