package co.unobot.uno.integrations.facebook.models.message.outgoing;

import co.unobot.uno.integrations.facebook.models.message.Attachment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 07/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private String text;
    private Attachment attachment;
    @JsonProperty("quick_replies")
    private String quickReplies;
    private String metadata;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getQuickReplies() {
        return quickReplies;
    }

    public void setQuickReplies(String quickReplies) {
        this.quickReplies = quickReplies;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
