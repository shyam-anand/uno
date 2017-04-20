package co.unobot.uno.integrations.facebook.models.message;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by shyam on 02/04/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment<T extends AttachmentPayload> {

    private AttachmentType type;
    private T payload;

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
