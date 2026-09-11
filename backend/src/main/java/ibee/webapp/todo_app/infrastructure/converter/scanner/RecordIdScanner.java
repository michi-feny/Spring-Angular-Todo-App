package ibee.webapp.todo_app.infrastructure.converter.scanner;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;

import ibee.webapp.todo_app.dto.base.StringToDtoIdConvertable;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecordIdScanner {

    private final String rootPackage = "ibee.webapp.todo_app";

    @SuppressWarnings("unchecked")
    public Set<Class<? extends Record>> scanForConvertibleRecords() {
        Set<Class<? extends Record>> foundClasses = new HashSet<>();
        try {
            PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
            String pattern = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + 
                rootPackage.replace('.', '/') + "/**/*.class";
            
            Resource[] resources = scanner.getResources(pattern);
            SimpleMetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory();

            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = readerFactory.getMetadataReader(resource);
                    Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                    
                    if (clazz.isRecord() && StringToDtoIdConvertable.class.isAssignableFrom(clazz)) {
                        foundClasses.add((Class<? extends Record>) clazz);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan classpath for StringToDtoIdConvertable records", e);
        }
        return foundClasses;
    }
}
