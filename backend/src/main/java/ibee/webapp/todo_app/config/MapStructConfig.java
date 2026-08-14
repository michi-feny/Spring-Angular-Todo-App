package ibee.webapp.todo_app.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@MapperConfig(
        componentModel = 
            MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = 
            ReportingPolicy.ERROR //IGNORE TODO: READ WHAT OPTION IS BEST APPROACH
)
public interface MapStructConfig {

}
