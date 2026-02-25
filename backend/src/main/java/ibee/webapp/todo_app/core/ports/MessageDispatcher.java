package ibee.webapp.todo_app.core.ports;

import ibee.webapp.todo_app.core.event.DomainEvent;

public interface MessageDispatcher {
    void dispatch(DomainEvent event) throws Exception;
}