package models.EventTemplate;

public abstract class GameEvent {

    // Template method
    public final void triggerEvent() {
        preEvent();
        executeEvent();
        postEvent();
    }

    // Concrete method: This can be shared by all events
    protected void preEvent() {
        System.out.println("Event is about to happen...");
    }

    // Abstract method: To be implemented by specific event types
    protected abstract void executeEvent();

    // Concrete method: Shared by all events
    protected void postEvent() {
        System.out.println("Event has concluded.");
    }
}