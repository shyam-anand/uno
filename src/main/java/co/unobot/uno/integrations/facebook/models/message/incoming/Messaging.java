package co.unobot.uno.integrations.facebook.models.message.incoming;

import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;

/**
 * Created by shyam on 02/04/17.
 */
public class Messaging {
    private FBUser sender;
    private FBPage recipient;
    private String timestamp;
    private Message message;

    public FBUser getSender() {
        return sender;
    }

    public void setSender(FBUser sender) {
        this.sender = sender;
    }

    public FBPage getRecipient() {
        return recipient;
    }

    public void setRecipient(FBPage recipient) {
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
