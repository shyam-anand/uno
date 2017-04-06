package co.unobot.uno.integrations.messenger.models.message.outgoing;

/**
 * Created by shyam on 07/04/17.
 */
public enum NotificationType {
    REGULAR("regular"),
    SILENT_PUSH("silent_push"),
    NO_PUSH("no_push");

    private String name;

    NotificationType(String n) {
        this.name = n;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
