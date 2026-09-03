package ibee.webapp.todo_app.features.person.related.workExp.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MergeWorkExperiencesRequestDto(
    @NotNull
    Long personId,

    @NotNull
    @Size(min = 2, message = "Must select at least two records to merge")
    List<Long> workExpIdsToMerge,

    @NotNull
    WorkExperienceDto newMasterDetails
) {
}  
