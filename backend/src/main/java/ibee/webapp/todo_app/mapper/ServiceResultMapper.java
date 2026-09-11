package ibee.webapp.todo_app.mapper;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;


@Mapper
public interface ServiceResultMapper {

    static <D, E> ServiceResult<D> toDto(
            ServiceResult<E> result,
            BaseMapper<D, E> mapper) {

        if (result.isRejected()) {
            return ServiceResult.rejected(
                result.violations()
            );
        }

        return ServiceResult.success(
            mapper.toDto(result.value())
        );
    }

}
