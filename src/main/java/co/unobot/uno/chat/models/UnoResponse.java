package co.unobot.uno.chat.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by shyam on 30/03/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnoResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("action")
    private AgentAction action;

    @JsonProperty("score")
    private float score;

    @JsonProperty("parameters")
    private Map<String, Map<String, Object>> parameters;

    public UnoResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public AgentAction getAction() {
        return action;
    }

    public void setAction(AgentAction action) {
        this.action = action;
    }

    public void setAction(String action) {
        if (action == null || action.isEmpty() || action.equals("") || !action.contains(".") || action.equals("null")) {
            this.action = null;
        } else {
            this.action = AgentAction.fromString(action);
        }
    }

    public Map<String, Map<String, Object>> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Map<String, Object>> parameters) {
        this.parameters = parameters;
    }
}
