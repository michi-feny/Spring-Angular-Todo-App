package ibee.webapp.todo_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.CountryTranslation;


import org.springframework.context.i18n.LocaleContextHolder;

import ibee.webapp.todo_app.core.entity.Country;

@Mapper(config = MapStructConfig.class)
public interface CountryTranslationMapper {

    @Named("extractTranslatedName")
    default String extractTranslatedName(Country country) {
        if (country == null) return null;
        
        // Grab the browser language on the fly (e.g., "en", "de")
        String currentLang = getBrowserLanguage();
        CountryTranslation translation = country.getActiveTranslation(currentLang);
        
        return translation != null ? translation.getName() : null;
    }

    @Named("extractLanguageCode")
    default String extractLanguageCode(Country country) {
        if (country == null) return null;
        
        // Grab the browser language on the fly
        String currentLang = getBrowserLanguage();
        CountryTranslation translation = country.getActiveTranslation(currentLang);
        
        return translation != null ? translation.getLanguageCode() : null;
    }

    default String getBrowserLanguage(){
        return LocaleContextHolder.getLocale().getLanguage();
    }
}
