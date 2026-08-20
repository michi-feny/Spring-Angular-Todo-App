package ibee.webapp.todo_app.dbSeeder;


import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.core.entity.CountryTranslation;
import ibee.webapp.todo_app.core.repository.CountryRepository;
import ibee.webapp.todo_app.core.repository.CountryTranslationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final CountryTranslationRepository translationRepository;

    public DatabaseSeeder(CountryRepository countryRepository, CountryTranslationRepository translationRepository) {
        this.countryRepository = countryRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // 1. Lade alle existierenden Länder
        Map<String, Country> existingCountries = countryRepository.findAll().stream()
                .collect(Collectors.toMap(Country::getCode, c -> c));

        // 2. Finde heraus, welche Sprach-Codes (z.B. "de", "en") BEREITS in der DB sind
        Set<String> languagesInDb = translationRepository.findAll().stream()
                .map(CountryTranslation::getLanguageCode)
                .collect(Collectors.toSet());

        // 3. Finde heraus, welche Sprachen aus den Properties-Dateien gelesen werden (Soll-Zustand)
        Set<Locale> targetLocales = detectImplementedLocales();
        
        // 4. Bilde das Delta: Welche Sprachen GIBT ES NOCH NICHT in der DB? (z.B. nur "es")
        Set<Locale> missingLocales = targetLocales.stream()
                .filter(locale -> !languagesInDb.contains(locale.getLanguage()))
                .collect(Collectors.toSet());

        System.out.println("Ziel-Sprachen: " + targetLocales.stream().map(Locale::getLanguage).toList());
        System.out.println("Fehlende Sprachen in DB: " + missingLocales.stream().map(Locale::getLanguage).toList());

        List<Country> newCountries = new ArrayList<>();
        List<CountryTranslation> newTranslations = new ArrayList<>();
        
        // 5. ISO-Länder durchgehen
        String[] isoCountries = Locale.getISOCountries();
        
        for (String code : isoCountries) {
            Country country = existingCountries.get(code);

            if (country == null) {
                // FALL A: Ein komplett neues Land
                country = new Country(code); 
                newCountries.add(country);
                
                // Für ein neues Land brauchen wir ALLE Ziel-Sprachen
                for (Locale locale : targetLocales) {
                    newTranslations.add(createTranslation(country, code, locale));
                }
            } else {
                // FALL B: Land existiert bereits
                // Nur ausführen, wenn wir tatsächlich eine NEUE Sprache gefunden haben
                if (!missingLocales.isEmpty()) {
                    for (Locale missingLocale : missingLocales) {
                        newTranslations.add(createTranslation(country, code, missingLocale));
                    }
                }
            }
        }
        
        // 6. Speichern
        if (!newCountries.isEmpty()) countryRepository.saveAll(newCountries);
        if (!newTranslations.isEmpty()) translationRepository.saveAll(newTranslations);

        if (!newCountries.isEmpty() || !newTranslations.isEmpty()) {
            System.out.println("Erfolgreich " + newCountries.size() + " Länder und " + newTranslations.size() + " Übersetzungen hinzugefügt.");
        } else {
            System.out.println("Alle Länder und Sprachen sind bereits auf dem neuesten Stand.");
        }
    }

    // Hilfsmethode, um den Code oben lesbarer zu machen
    private CountryTranslation createTranslation(Country country, String code, Locale targetLocale) {
        Locale countryLocale = Locale.of("", code);
        String localizedName = countryLocale.getDisplayCountry(targetLocale);
        
        if (localizedName == null || localizedName.isEmpty()) {
            localizedName = code; 
        }
        
        return new CountryTranslation(null, targetLocale.getLanguage(), localizedName, country);
    }

    private Set<Locale> detectImplementedLocales() throws Exception {
        Set<Locale> locales = new HashSet<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:messages*.properties");
        
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            if (filename != null) {
                if (filename.equals("messages.properties")) {
                    locales.add(Locale.ENGLISH); 
                } else if (filename.startsWith("messages_") && filename.endsWith(".properties")) {
                    String langCode = filename.substring(9, filename.length() - 11);
                    locales.add(Locale.of(langCode));
                }
            }
        }
        return locales;
    }
}