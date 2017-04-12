package co.unobot.uno.integrations.facebook.models.message.outgoing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 02/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FBOutgoingMessage {

    private Recipient recipient;
    private Message message;
    @JsonProperty("sender_action")
    private SenderAction senderAction;
    @JsonProperty("notification_type")
    private NotificationType notificationType;

    public FBOutgoingMessage() {
    }

    public FBOutgoingMessage(String recipientId, String messageText) {
        this.recipient = new Recipient();
        this.recipient.setId(recipientId);

        this.message = new Message();
        this.message.setText(messageText);
    }

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
