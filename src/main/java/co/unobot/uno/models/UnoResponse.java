package co.unobot.uno.models;

import ai.api.model.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shyam on 30/03/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnoResponse {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(UnoResponse.class);

    @JsonProperty("message")
    private String message;

    @JsonProperty("action")
    private String action;

    @JsonProperty("score")
    private float score;

    @JsonProperty("parameters")
    private List<UnoResponseParameters> unoResponseParametersList;

    public UnoResponse(Result result) {
        action = result.getAction();
        score = result.getScore();
        message = result.getFulfillment().getSpeech();

        if (!action.equals("smalltalk.greetings") && !result.getContexts().isEmpty()) {
            unoResponseParametersList = result.getContexts().stream().map(context -> {
                UnoResponseParameters unoResponseParameters = new UnoResponseParameters();
                Map<String, JsonElement> contextParams = context.getParameters();
                contextParams.forEach((key, value) -> {

                    if (!key.contains(".original")) {
                        if (value == null) {
                            logger.info(key + " -> null");
                        } else {
                            logger.info(key + " -> " + value);
                            List<Map<String, String>> values = new ArrayList<>();
                            if (value.isJsonArray()) {
                                value.getAsJsonArray().forEach(v -> {
                                    Map<String, String> element = new HashMap<>();
                                    String original = contextParams.get(key.concat(".original")).getAsString();
                                    element.put(v.getAsString(), original);
                                    values.add(element);
                                });
                            } else {
                                Map<String, String> element = new HashMap<>();
                                String original = contextParams.get(key.concat(".original")).getAsString();
                                element.put(value
                                        .getAsString(), original);
                                values.add(element);
                            }

                            switch (key) {
                                case "number":
                                    unoResponseParameters.setNumber(values);
                                    break;
                                case "product":
                                    unoResponseParameters.setProduct(values);
                                    break;
                                case "business":
                                    unoResponseParameters.setBusiness(values);
                            }
                        }
                    }
                });
                return unoResponseParameters;
            }).collect(Collectors.toList());
        }
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<UnoResponseParameters> getUnoResponseParametersList() {
        return unoResponseParametersList;
    }

    public void setUnoResponseParametersList(List<UnoResponseParameters> unoResponseParametersList) {
        this.unoResponseParametersList = unoResponseParametersList;
    }
}
