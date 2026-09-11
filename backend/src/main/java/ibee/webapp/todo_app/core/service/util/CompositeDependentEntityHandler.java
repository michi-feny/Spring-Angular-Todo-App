package ibee.webapp.todo_app.core.service.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacadeService;


public interface CompositeDependentEntityHandler<PARENT, PARENT_ID, CHILD, CHILD_ID> {

    CHILD extractChild(PARENT parent);
    void applyChild(PARENT parent, CHILD child);
    CHILD_ID extractChildId(CHILD child);
    CHILD_ID extractChildIdFromComposite(PARENT_ID parentId);
    PARENT_ID buildNewCompositeId(PARENT_ID oldId, CHILD_ID newChildId);
    void applyCompositeId(PARENT parent, PARENT_ID id);
    PARENT instantiateNewParent();

    // CREATE Flow (Unchanged)
    default PARENT resolveChildAndPersistNewLink(
            PARENT entity,
            MyCrudBaseEntityFacadeService<CHILD, CHILD_ID> childService,
            Function<PARENT, PARENT> createAction) {

        CHILD requestedChild = extractChild(entity);
        if (requestedChild != null) {
            CHILD resolvedChild = Optional.ofNullable(extractChildId(requestedChild))
                    .flatMap(childService::findByIdWithoutException)
                    .orElseGet(() -> childService.create(requestedChild));
            applyChild(entity, resolvedChild);
        }
        return createAction.apply(entity);
    }

    // UPDATE Flow (Pure ID swapping, NO preSaveHook)
    default PARENT resolveChildAndPersistUpdate(
            PARENT incomingUpdates,
            PARENT_ID currentId,
            MyCrudBaseEntityFacadeService<CHILD, CHILD_ID> childService,
            BiFunction<PARENT, PARENT_ID, PARENT> updateAction,
            Function<PARENT, PARENT> createAction,
            Function<PARENT_ID, Optional<PARENT>> originalEntityFinder,
            BiConsumer<PARENT, PARENT> stateCopier,
            Consumer<PARENT_ID> deleteAction) {
        
        CHILD requestedChild = extractChild(incomingUpdates);

        // Scenario 1: No child payload
        if (requestedChild == null) {
            return updateAction.apply(incomingUpdates, currentId);
        }

        CHILD_ID oldChildId = extractChildIdFromComposite(currentId);
        CHILD resolvedChild = childService.update(requestedChild, oldChildId);
        applyChild(incomingUpdates, resolvedChild);

        CHILD_ID newChildId = extractChildId(resolvedChild);

        // Scenario 2: Child ID didn't change
        if (newChildId.equals(oldChildId)) {
            return updateAction.apply(incomingUpdates, currentId);
        }

        // Scenario 3: Child ID changed -> Deduplication Swap
        PARENT originalEntity = originalEntityFinder.apply(currentId)
            .orElseThrow(() -> new IllegalArgumentException("Original entity not found"));

        PARENT newLink = instantiateNewParent();
        stateCopier.accept(originalEntity, newLink);
        stateCopier.accept(incomingUpdates, newLink);

        PARENT_ID newId = buildNewCompositeId(currentId, newChildId);
        applyCompositeId(newLink, newId);
        applyChild(newLink, resolvedChild);

        deleteAction.accept(currentId);
        
        return createAction.apply(newLink);
    }
}
