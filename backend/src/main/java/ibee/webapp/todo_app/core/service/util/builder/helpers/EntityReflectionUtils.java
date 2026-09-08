package ibee.webapp.todo_app.core.service.util.builder.helpers;

import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

import java.lang.reflect.Method;
import java.util.Objects;


public final class EntityReflectionUtils {

    private EntityReflectionUtils() {
        // Prevent instantiation
    }

    /**
     * Extracts the entity generic type and formats it as an i18n key.
     */
    public static String generateEntityKey(
            Class<?> serviceImplementationClass, 
            Class<?> baseServiceClass) {
                
        ResolvableType type = ResolvableType.forClass(serviceImplementationClass)
                                            .as(baseServiceClass);

        Class<?> entityClass = type.getGeneric(0).resolve();

        Objects.requireNonNull(
            entityClass, 
            "Could not resolve ENTITY type for " + serviceImplementationClass.getName()
        );

        return "entity." + StringUtils.uncapitalize(entityClass.getSimpleName());
    }

    /**
     * Dynamically extracts the ID from the entity using Spring ReflectionUtils.
     * Looks for @Id, @EmbeddedId, getId(), or id() for Java Records.
     */
    @SuppressWarnings("unchecked")
    public static <ID> ID extractIdDynamically(Object entity) {
        if (entity == null) {
            return null;
        }
        
        final Object[] idHolder = new Object[1];
        
        // 1. Check for standard JPA annotations on fields
        ReflectionUtils.doWithFields(
            entity.getClass(), 
            field -> {
                ReflectionUtils.makeAccessible(field);
                idHolder[0] = ReflectionUtils.getField(field, entity);
            }, 
            field -> field.isAnnotationPresent(Id.class) 
                  || field.isAnnotationPresent(EmbeddedId.class)
        );
        
        if (idHolder[0] != null) {
            return (ID) idHolder[0];
        }

        // 2. Fallback: Check if there's a getter method named "getId" (Standard Classes)
        Method getIdMethod = ReflectionUtils.findMethod(
            entity.getClass(), 
            "getId"
        );
        
        if (getIdMethod != null) {
            ReflectionUtils.makeAccessible(getIdMethod);
            return (ID) ReflectionUtils.invokeMethod(getIdMethod, entity);
        }

        // 3. Fallback: Check if there's an accessor named "id" (Java Records)
        Method recordIdMethod = ReflectionUtils.findMethod(
            entity.getClass(), 
            "id"
        );
        
        if (recordIdMethod != null) {
            ReflectionUtils.makeAccessible(recordIdMethod);
            return (ID) ReflectionUtils.invokeMethod(recordIdMethod, entity);
        }

        return null;
    }
}
