package ibee.webapp.todo_app.core.service.util.builder;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;



public class CrudEntityAsserterBuilder {

    private final String pureEntityName;
    private static final String ASSERT_MSG_ENTITY_NULL = "%s entity cannot be null";
    private static final String ASSERT_MSG_ID_NULL = "%s ID [%s] cannot be null";
    private static final String ASSERT_MSG_UPDATES_NULL = "%s source updates cannot be null";
    private static final String ASSERT_MSG_LIST_NULL = "%s list/iterable cannot be null";
    private static final String ASSERT_MSG_CONTEXT_NULL = "contextName cannot be null";
    

    public CrudEntityAsserterBuilder(String pureEntityName) {
        this.pureEntityName = pureEntityName;
        
    }

    public void entityNotNull(Object entity) {
        Assert.notNull(
            entity, 
            String.format(ASSERT_MSG_ENTITY_NULL, pureEntityName)
        );
    }

    public void idNotNull(Object id) {
        Assert.notNull(
            id, 
            String.format(ASSERT_MSG_ID_NULL, pureEntityName, id)
        );
    }

    public void updatesNotNull(Object sourceUpdates) {
        Assert.notNull(
            sourceUpdates, 
            String.format(ASSERT_MSG_UPDATES_NULL, pureEntityName)
        );
    }

    public void iterableNotNull(Iterable<?> iterable) {
        Assert.notNull(
            iterable, 
            String.format(ASSERT_MSG_LIST_NULL, pureEntityName)
        );
    }

    public void contextNotNull(String contextName) {
        Assert.notNull(
            contextName, 
            ASSERT_MSG_CONTEXT_NULL
        );
    }
}