package co.unobot.uno.integrations.messenger.models.message.incoming;

import co.unobot.uno.integrations.messenger.models.message.Attachment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shyam on 02/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String mid;
    private int seq;
    private String text;
    @JsonProperty("sticker_id")
    private long stickerId;
    private List<Attachment> attachments;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public long getStickerId() {
        return stickerId;
    }

    public void setStickerId(long stickerId) {
        this.stickerId = stickerId;
    }
}
