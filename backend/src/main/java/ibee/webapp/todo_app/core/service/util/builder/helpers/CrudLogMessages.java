package ibee.webapp.todo_app.core.service.util.builder.helpers;

import org.springframework.stereotype.Component;


public final class CrudLogMessages {

    private CrudLogMessages() {
        // Prevent instantiation
    }

    public static final String LOG_CREATE_SUCCESS = """
            CREATE_SUCCESS: New {} \
            created successfully.\
            """;

    public static final String LOG_UPDATE_SUCCESS = """
            UPDATE_SUCCESS: {} with ID {} \
            was updated.\
            """;

    public static final String LOG_DELETE_SUCCESS = """
            DELETE_SUCCESS: {} with ID {} \
            was deleted.\
            """;

    public static final String LOG_DELETE_ENTITY_SUCCESS = """
            DELETE_SUCCESS: {} record \
            deleted.\
            """;

    public static final String LOG_DELETE_FAILED = """
            DELETE_FAILED: {} with ID {} \
            is constraint-violated.\
            """;

    public static final String LOG_DELETE_ENTITY_FAILED = """
            DELETE_FAILED: {} record \
            constraint violation.\
            """;

    public static final String LOG_BATCH_SAVE_SUCCESS = """
            BATCH_SAVE_SUCCESS: Multiple {} \
            records saved.\
            """;

    public static final String LOG_BATCH_DELETE_SUCCESS = """
            BATCH_DELETE_SUCCESS: Multiple {} \
            records deleted by ID.\
            """;

    public static final String LOG_BATCH_DELETE_ENTITIES_SUCCESS = """
            BATCH_DELETE_SUCCESS: Multiple {} \
            records deleted.\
            """;

    public static final String LOG_NOT_FOUND_UPDATE = """
            {} with ID {} was not found \
            for update.\
            """;

    public static final String LOG_NOT_FOUND_DELETE = """
            {} with ID {} was not found \
            for deletion.\
            """;

    public static final String LOG_NOT_FOUND_FIND = """
            {} with ID {} was not found.\
            """;

    public static final String LOG_NOT_FOUND_FIND_ALL = """
            No {} records were found.\
            """;

    public static final String LOG_FOUND_SUCCESS = "Successfully found {} with ID: {}";
    public static final String LOG_FOUND_ALL_SUCCESS = "Successfully found {} records for IDs: {}";
    public static final String LOG_EXISTS_CHECK = "Checked existence for {} with ID: {} -> {}";


    public static final String EX_MSG_RESOURCE_NOT_FOUND = "Resource not found";
    public static final String I18N_KEY_NOT_FOUND_SINGLE = "crud.notFound.single";
    public static final String I18N_KEY_NOT_FOUND_MULTIPLE = "crud.notFound.multiple";
    public static final String I18N_KEY_NOT_FOUND_ALL = "crud.notFound.all";

    public static final String ERR_MSG_CONSTRAINT_VIOLATION = """
            Cannot delete this record because \
            it is actively referenced by other entities.\
            """;

   
    /**
     * Builds a standard assertion message (e.g., "hardSkill cannot be null").
     */
    public static String msg(String pureEntityName, String baseText) {
        String name = (pureEntityName != null && !pureEntityName.isEmpty()) ? pureEntityName : "entity";
        return name + " " + baseText;
    }

    /**
     * Builds an assertion message including an identifier when available 
     * (e.g., "hardSkill with ID [123] cannot be null").
     */
    public static String msgWithId(String pureEntityName, Object identifier, String baseText) {
        if (identifier == null) {
            return msg(pureEntityName, baseText);
        }
        String name = (pureEntityName != null && !pureEntityName.isEmpty()) ? pureEntityName : "entity";
        return name + " with ID [" + identifier + "] " + baseText;
    }
}
