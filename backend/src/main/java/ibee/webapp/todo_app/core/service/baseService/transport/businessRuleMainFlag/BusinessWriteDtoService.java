package ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag;

import org.springframework.validation.annotation.Validated;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;

@Validated
public interface BusinessWriteDtoService<DTO, IDDTO> {
    ServiceResult<DTO> create(@Validated(OnCreate.class) DTO dto);
    ServiceResult<DTO> update(@Validated(OnUpdate.class) DTO dto, IDDTO id);
}
