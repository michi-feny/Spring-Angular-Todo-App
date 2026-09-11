package ibee.webapp.todo_app.core.service.baseService.transport;

import org.springframework.validation.annotation.Validated;

import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;

// 3. Standard Write (No invariants, returns standard DTO)
@Validated
public interface StandardWriteDtoService<DTO, IDDTO> {
    DTO create(@Validated(OnCreate.class) DTO dto);
    DTO update(@Validated(OnUpdate.class) DTO dto, IDDTO id);
}
