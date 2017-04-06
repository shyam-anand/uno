package co.unobot.uno.integrations.messenger.models.message.outgoing;

import co.unobot.uno.integrations.messenger.models.message.incoming.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 02/04/17.
 */
public class FBOutgoingMessage {

    private Recipient recipient;
    private Message message;
    @JsonProperty("sender_action")
    private SenderAction senderAction;
    @JsonProperty("notification_type")
    private NotificationType notificationType;

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public SenderAction getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(SenderAction senderAction) {
        this.senderAction = senderAction;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
