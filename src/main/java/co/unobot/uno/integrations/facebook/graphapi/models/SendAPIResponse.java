package co.unobot.uno.integrations.facebook.graphapi.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 09/04/17.
 */
public class SendAPIResponse {
    @JsonProperty("recipient_id")
    private String recipientId;

    @JsonProperty("mid")
    private String mid;

    @JsonCreator
    public SendAPIResponse(@JsonProperty("recipient_id") String recipientId, @JsonProperty("mid") String mid) {
        this.recipientId = recipientId;
        this.mid = mid;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
