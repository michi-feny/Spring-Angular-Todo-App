package ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.validation.annotation.Validated;
import ibee.webapp.todo_app.core.result.BusinessViolation;
import ibee.webapp.todo_app.core.result.ServiceResult;

@Validated
public interface MainFlagBusinessRuleService<DTO, ENTITY>{

    default ServiceResult<DTO> enforceSingleMainRuleOnCreate(
            ENTITY entity,
            Predicate<ENTITY> isMainCheck,
            Function<ENTITY, Long> personIdExtractor,
            Function<Long, Optional<ENTITY>> existingMainFinder,
            Function<ENTITY, ENTITY> saveAction,
            Function<ENTITY, DTO> toDtoMapper,
            String violationCode,
            String violationMessage) {

        if (isMainCheck.test(entity)) {
            Long personId = personIdExtractor.apply(entity);
            Optional<ENTITY> existingMain = existingMainFinder.apply(personId);

            if (existingMain.isPresent()) {
                DTO existingDto = toDtoMapper.apply(existingMain.get());
                return ServiceResult.rejected(
                        existingDto,
                        List.of(new BusinessViolation(violationCode, violationMessage))
                );
            }
        }

        ENTITY saved = saveAction.apply(entity);
        return ServiceResult.success(toDtoMapper.apply(saved));
    }

    default <ID> ServiceResult<DTO> enforceSingleMainRuleOnUpdate(
            ENTITY entity,
            ID entityId,
            Predicate<ENTITY> isMainCheck,
            Function<ENTITY, Long> personIdExtractor,
            Function<ID, Optional<ENTITY>> currentEntityFinder,
            Function<Long, Optional<ENTITY>> existingMainFinder,
            BiFunction<ENTITY, ID, ENTITY> updateAction,
            Function<ENTITY, DTO> toDtoMapper,
            String violationCode,
            String violationMessage) {

        if (isMainCheck.test(entity)) {
            Long personId = personIdExtractor.apply(entity);
            Optional<ENTITY> existingMain = existingMainFinder.apply(personId);

            if (existingMain.isPresent()) {
                ENTITY current = currentEntityFinder.apply(entityId).orElse(null);
                DTO existingDto = toDtoMapper.apply(existingMain.get());
                if (current == null || !existingMain.get().equals(current)) {
                    return ServiceResult.rejected(
                            existingDto,
                            List.of(new BusinessViolation(violationCode, violationMessage))
                    );
                }
            }
        }

        ENTITY updated = updateAction.apply(entity, entityId);
        return ServiceResult.success(toDtoMapper.apply(updated));
    }
}
