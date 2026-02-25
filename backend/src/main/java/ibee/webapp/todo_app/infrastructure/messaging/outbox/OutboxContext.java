package ibee.webapp.todo_app.infrastructure.messaging.outbox;

public class OutboxContext {
    private static final ThreadLocal<Boolean> runningFromOutbox = ThreadLocal.withInitial(() -> false);

    public static void setRunning(boolean active) {
        runningFromOutbox.set(active);
    }

    public static boolean isActive() {
        return runningFromOutbox.get();
    }
}