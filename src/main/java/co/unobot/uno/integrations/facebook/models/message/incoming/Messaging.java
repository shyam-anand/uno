package co.unobot.uno.integrations.facebook.models.message.incoming;

import co.unobot.uno.integrations.facebook.models.FBUser;

/**
 * Created by shyam on 02/04/17.
 */
public class Messaging {
    private FBUser sender;
    private FBUser recipient;
    private String timestamp;
    private Message message;

    public FBUser getSender() {
        return sender;
    }

    public void setSender(FBUser sender) {
        this.sender = sender;
    }

    public FBUser getRecipient() {
        return recipient;
    }

    public void setRecipient(FBUser recipient) {
        this.recipient = recipient;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
