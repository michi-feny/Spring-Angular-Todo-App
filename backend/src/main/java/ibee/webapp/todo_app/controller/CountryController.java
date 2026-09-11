package ibee.webapp.todo_app.controller;
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.core.service.CountryServiceImpl;
import ibee.webapp.todo_app.dto.CountryDto;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.mapper.CountryMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryServiceImpl countryService;
    private final TranslationService translationService;
    private final CountryMapper countryMapper;

    public CountryController(
        CountryServiceImpl countryService, //TODO: CREATE OWN COUNTRYDTOSERVICEIMPL, so that this mapper is not needed here!
        TranslationService translationService,
        CountryMapper countryMapper
    ) {
        this.countryService = countryService;
        this.translationService = translationService;
        this.countryMapper = countryMapper;
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<CountryDto>>> getAll(
            // Safely extracts the language from the Accept-Language header, defaulting if absent
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "en") Locale locale) {
        
        // Pass the language/locale down to the service to fetch the localized list
        List<CountryDto> countries = countryMapper.toDtoList(countryService.findAll());//chack that correct naming is still working
        
        String message = translationService.translate("crud.loadedAll", "entity.country");
        
        // No HATEOAS assembler needed for a simple dropdown lookup list
        return buildResponse(countries, message);
    }
}
