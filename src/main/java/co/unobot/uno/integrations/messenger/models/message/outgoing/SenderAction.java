package co.unobot.uno.integrations.messenger.models.message.outgoing;

/**
 * Created by shyam on 07/04/17.
 */
public enum SenderAction {
    TYPING_ON("typing_on"),
    TYPING_OFF("typing_off"),
    MARK_SEEN("mark_seen");

    private String name;

    SenderAction(String n) {
        this.name = n;
    }


    @Override
    public String toString() {
        return this.name;
    }

}
