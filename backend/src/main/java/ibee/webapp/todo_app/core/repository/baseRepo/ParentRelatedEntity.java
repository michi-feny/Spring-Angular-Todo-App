package ibee.webapp.todo_app.core.repository.baseRepo;

public interface ParentRelatedEntity<PARENT_ID> {
    PARENT_ID getParentId();
}