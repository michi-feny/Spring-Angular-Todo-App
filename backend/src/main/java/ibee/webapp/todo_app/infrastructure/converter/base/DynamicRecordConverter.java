package ibee.webapp.todo_app.infrastructure.converter.base;

import org.springframework.core.convert.converter.Converter;
import java.lang.reflect.RecordComponent;
/*
DynamicRecordConverter does not have a 
@Component annotation because it is a template/generic utility class 
that needs to be instantiated dynamically at runtime, 
rather than being a single, standalone Spring bean.

Here is why it is designed this way:

1. It Needs a Specific targetClass (Parameterized Generics)
Spring beans managed by @Component are singletons created 
automatically by component scanning. 
They are generic to the whole class.
However, DynamicRecordConverter requires a specific 
target record class passed into its constructor 
(e.g., new DynamicRecordConverter<>(PersonAddressDtoId.class)) 
so it knows which record components to reflect and parse. 
If it were a standard @Component, 
Spring wouldn't know which record type it is supposed to convert for.
*/
public class DynamicRecordConverter<T extends Record> implements Converter<String, T> {
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
