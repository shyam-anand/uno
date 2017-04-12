package co.unobot.uno.integrations.facebook.models.message.incoming;

/**
 * Created by shyam on 02/04/17.
 */
public enum MessageObject {

    PAGE("page");

    private String name;

    MessageObject(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
