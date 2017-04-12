package co.unobot.uno.integrations.facebook.models.message.outgoing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 07/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient {
    private String id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
