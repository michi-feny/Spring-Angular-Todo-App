package ibee.webapp.todo_app.config;
/* 
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.infrastructure.converter.base.DynamicRecordConverter;
import ibee.webapp.todo_app.infrastructure.converter.scanner.RecordIdScanner;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.RecordComponent;
import java.util.List;

import ibee.webapp.todo_app.core.dto.base.StringToDtoIdConvertable;
*/
import ibee.webapp.todo_app.infrastructure.converter.base.DynamicRecordConverter;
import ibee.webapp.todo_app.infrastructure.converter.scanner.RecordIdScanner;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;

import java.util.Set;


@Configuration
public class AutoSafeIdConvertersConfig {

    private final RecordIdScanner recordIdScanner;
    private final ConfigurableConversionService conversionService;

    public AutoSafeIdConvertersConfig(
            RecordIdScanner recordIdScanner,
            ConfigurableConversionService conversionService) {
        this.recordIdScanner = recordIdScanner;
        this.conversionService = conversionService;
    }

    @PostConstruct
    public void registerConverters() {
        Set<Class<? extends Record>> discoveredRecords = recordIdScanner.scanForConvertibleRecords();

        for (Class<? extends Record> recordClass : discoveredRecords) {
            if (recordClass.getRecordComponents().length == 0) {
                throw new IllegalStateException(
                    "Startup Error: ID record " + recordClass.getName() + " implements StringToDtoIdConvertable but has no components!"
                );
            }
            // Directly register the converter into Spring's master ConversionService
            //conversionService.addConverter(String.class, recordClass, new DynamicRecordConverter<>(recordClass));
            registerTypedConverter(recordClass);
        }
    }

        /**
     * Helper method to capture wildcard <capture#-of ? extends Record> into concrete type <T>
     */
    private <T extends Record> void registerTypedConverter(Class<T> recordClass) {
        conversionService.addConverter(String.class, recordClass, new DynamicRecordConverter<>(recordClass));
    }


    /**
     * =================================================================================
     * C E N T R A L   R E C O R D   R E G I S T R Y
     * =================================================================================
     * Add any new DTO ID record class to this list. 
     * You will never need to create a separate converter class again!
     * 
     * NOW IT ALL RUNS AT SPRING BUILD AND SEARCHES FOR THE 
     * StringToDtoIdConvertable INTERFACE inside the DTOId representations
     */
    //private final List<Class<? extends Record>> registeredIdRecordClasses = List.of(
     /*    PersonAddressDtoId.class,
        PersonPhoneNumberDtoId.class,
        PersonEmailAddressDtoId.class,
        PersonCountryDtoId.class,
        PersonAdditionalHardSkillDtoId.class,
        PersonDegreeDtoId.class,
        PersonProfessionQualificationDtoId.class,
        PersonSoftSkillDtoId.class
    */
        // Example for future keys: 
        // OrderDtoId.class,
        // InvoiceLineDtoId.class
   // );
/* 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (Class<? extends Record> recordClass : registeredIdRecordClasses) {
            // Calls a type-safe helper method to avoid generic compiler warnings
            registerConverter(registry, recordClass);
        }
    }
*/
    /**
     * Helper method to capture the wildcard generic type <T> safely, 
     * satisfying the FormatterRegistry compiler requirements.
     */
 /*    private <T extends Record> void registerConverter(FormatterRegistry registry, Class<T> recordClass) {
        registry.addConverter(String.class, recordClass, new DynamicRecordConverter<>(recordClass));
    }
*/
    /**
     * Internal generic converter that parses path segments and instantiates the target record.
     */
 /*    private static class DynamicRecordConverter<T extends Record> implements Converter<String, T> {
        private final Class<T> targetClass;

        public DynamicRecordConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isBlank()) return null;

            String[] parts = source.split("_");
            RecordComponent[] components = targetClass.getRecordComponents();

            if (parts.length != components.length) {
                throw new IllegalArgumentException(
                    "Invalid ID format for " + targetClass.getSimpleName() + 
                    ". Expected " + components.length + " segments separated by '_'."
                );
            }

            try {
                Object[] args = new Object[components.length];
                Class<?>[] parameterTypes = new Class[components.length];

                for (int i = 0; i < components.length; i++) {
                    parameterTypes[i] = components[i].getType();
                    args[i] = castValue(parts[i], parameterTypes[i]);
                }

                return targetClass.getDeclaredConstructor(parameterTypes).newInstance(args);

            } catch (Exception e) {
                throw new RuntimeException("Failed to convert string path '" + source + "' to " + targetClass.getName(), e);
            }
        }

        private Object castValue(String value, Class<?> type) {
            if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(value);
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.parseInt(value);
            }
            return value;
        }
    }
        */
}