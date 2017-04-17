package co.unobot.uno.chat.models;

import co.unobot.uno.users.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 30/03/17.
 */
public class IncomingMessage {

    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
