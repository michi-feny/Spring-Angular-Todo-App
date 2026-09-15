package ibee.webapp.todo_app.features.person.related.workExp.dto;

import java.util.List;

import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record MergeWorkExperiencesRequestDto(
    @NotNull
    Long personId,

    @NotNull
    @Size(min = 2, message = "Must select at least two records to merge")
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    List<Long> workExpIdsToMerge,

    @NotNull
    WorkExperienceDto newMasterDetails
) {
}  
