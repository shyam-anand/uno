package co.unobot.uno.integrations.facebook.models.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by shyam on 02/04/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment {

    private enum AttachmentType {
        IMAGE("image"),
        AUDIO("audio"),
        VIDEO("video"),
        FILE("file"),
        TEMPLATE("template");

        private String name;

        AttachmentType(String n) {
            this.name = n;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private class Payload {
        private String url;

        @JsonProperty("sticker_id")
        private long stickerId;

        @JsonProperty("is_reusable")
        private boolean isReusable;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getStickerId() {
            return stickerId;
        }

        public void setStickerId(long stickerId) {
            this.stickerId = stickerId;
        }
    }

    private AttachmentType type;
    private Map<String, String> payload;

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }
}
