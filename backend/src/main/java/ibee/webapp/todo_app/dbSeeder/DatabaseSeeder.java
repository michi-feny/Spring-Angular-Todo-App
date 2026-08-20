package ibee.webapp.todo_app.dbSeeder;


package ibee.webapp.todo_app.config; // bzw. seeder/bootstrap

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
        
        // 1. Hole alle existierenden Länder
        Map<String, Country> existingCountries = countryRepository.findAll().stream()
                .collect(Collectors.toMap(Country::getCode, c -> c));

        // 2. Hole alle existierenden Übersetzungen als Set (z.B. "AT-de", "AT-en")
        Set<String> existingTranslationKeys = translationRepository.findAll().stream()
                .map(t -> t.getCountry().getCode() + "-" + t.getLanguageCode())
                .collect(Collectors.toSet());

        // 3. Zielsprachen aus Properties-Dateien dynamisch auslesen
        Set<Locale> targetLocales = detectImplementedLocales();
        System.out.println("Detected implemented languages: " + targetLocales);

        List<Country> newCountries = new ArrayList<>();
        List<CountryTranslation> newTranslations = new ArrayList<>();
        
        // 4. ISO-Länder durchgehen
        String[] isoCountries = Locale.getISOCountries();
        
        for (String code : isoCountries) {
            
            // a) Prüfen: Existiert das Land schon?
            Country country = existingCountries.get(code);
            if (country == null) {
                country = new Country(code); 
                newCountries.add(country);
                existingCountries.put(code, country); // Direkt in die Map packen, falls unten benötigt
            }

            // b) Prüfen: Existieren alle benötigten Übersetzungen für dieses Land?
            for (Locale targetLocale : targetLocales) {
                String langCode = targetLocale.getLanguage();
                String translationKey = code + "-" + langCode; // z.B. "AT-es"

                // Wenn diese spezifische Sprache für dieses Land fehlt -> anlegen!
                if (!existingTranslationKeys.contains(translationKey)) {
                    Locale countryLocale = Locale.of("", code);
                    String localizedName = countryLocale.getDisplayCountry(targetLocale);
                    
                    if (localizedName == null || localizedName.isEmpty()) {
                        localizedName = code; 
                    }

                    CountryTranslation translation = new CountryTranslation(null, langCode, localizedName, country);
                    newTranslations.add(translation);
                }
            }
        }
        
        // 5. Speichern
        if (!newCountries.isEmpty()) {
            countryRepository.saveAll(newCountries);
            System.out.println("Integrated " + newCountries.size() + " NEW countries.");
        }
        
        if (!newTranslations.isEmpty()) {
            translationRepository.saveAll(newTranslations);
            System.out.println("Integrated " + newTranslations.size() + " NEW translations.");
        } 
        
        if (newCountries.isEmpty() && newTranslations.isEmpty()) {
            System.out.println("All ISO countries and translations are already up to date.");
        }
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
                    locales.add(Locale.of(langCode)); // Modernes Locale.of() genutzt
                }
            }
        }
        return locales;
    }
}