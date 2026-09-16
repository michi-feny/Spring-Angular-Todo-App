package ibee.webapp.todo_app.dto;

import java.time.LocalDate;

import ibee.webapp.todo_app.validation.dateRange.ValidDateRange;

@ValidDateRange(startField = "startDate", endField = "endDate")
public record LocalDateDurationDto(
    LocalDate startDate,
    LocalDate endDate
) {

}


