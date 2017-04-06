package co.unobot.uno.integrations.messenger.models.message.incoming;

import java.util.List;

/**
 * Created by shyam on 02/04/17.
 */
public class MessageEntry {
    private String id;
    private long time;
    private List<Messaging> messaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<Messaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }
}
