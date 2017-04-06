package co.unobot.uno.integrations.messenger.models.message.incoming;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by shyam on 02/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FBIncomingMessage {

    @JsonProperty("object")
    private String object;

    private List<MessageEntry> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<MessageEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<MessageEntry> entry) {
        this.entry = entry;
    }

    public List<MessageEntry> getEntries() {
        return entry;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "[Invalid JSON]";
        }
    }
}
