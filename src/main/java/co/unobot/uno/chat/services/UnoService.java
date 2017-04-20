package co.unobot.uno.chat.services;

import ai.api.model.Result;
import co.unobot.uno.ai.AIException;
import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.services.AIService;
import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.services.BusinessService;
import co.unobot.uno.chat.models.AgentAction;
import co.unobot.uno.chat.models.AgentActionCategory;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.Attachment;
import co.unobot.uno.integrations.facebook.models.message.incoming.Message;
import co.unobot.uno.integrations.facebook.models.message.templates.ButtonTemplate;
import co.unobot.uno.integrations.facebook.models.message.templates.buttons.PostbackButton;
import co.unobot.uno.integrations.facebook.services.MessengerSender;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         02/04/17
 */
@Service
public class UnoService {

    private static final Logger logger = LoggerFactory.getLogger(UnoService.class);

    @Autowired
    private AIService aiService;

    @Autowired
    private MessengerSender messenger;

    @Autowired
    private BusinessService businesses;

    private FBUser fbUser;
    private FBPage fbPage;
    private UnoResponse response;

    public void messageFromFB(@NotNull Message incomingFbMessage, @NotNull FBUser fbUser, @NotNull FBPage fbPage) {

        this.fbPage = fbPage;
        this.fbUser = fbUser;

        String mid = incomingFbMessage.getMid();
        int sequence = incomingFbMessage.getSeq();
        String message = incomingFbMessage.getText();
        List<Attachment> attachments = incomingFbMessage.getAttachments();
        if (attachments == null) {
            attachments = Collections.emptyList();
        }

        logger.info("[FacebookMessage] {} {} {} {} {} \"{}\"", fbUser.getId(), fbPage.getId(), mid, sequence, attachments.size(), message);

        Business business = businesses.getForFBPage(fbPage.getId());
        Agent agent = business.getAgent();

        //TODO Logging, user profiling, etc
        try {
            response = getAIResponse(agent, message, business.getName(), business.getId());

            AgentAction agentAction = response.getAction();
            if (agentAction.category().equals(AgentActionCategory.SMALLTALK)) {
                messenger.sendTextMessage(response.getMessage(), fbUser, fbPage);
            } else {
                switch (agentAction.category()) {
                    case DELIVERY:
                        handleDeliveryAction();
                        break;
                    default:
                        messenger.sendTextMessage(response.getMessage(), fbUser, fbPage);
                }
            }
        } catch (AIException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleDeliveryAction() {

        String text = response.getMessage().equals("") ? "Sorry, working in progress! Check back soon!" : response.getMessage();
        List<PostbackButton> buttons = new ArrayList<>();
        switch (response.getAction()) {
            case DELIVERY_PRODUCT_ADD:
                text = "Please confirm your order";
                PostbackButton confirmButton = new PostbackButton();
                Map<String, Object> payload = new HashMap<>();
                payload.put("action", "confirm");
                payload.put("parameters", response.getParameters());
                confirmButton.setPayload(payload);
                break;
        }

        ButtonTemplate<PostbackButton> template = new ButtonTemplate<>();
        template.setText(text);
        template.setButtons(buttons);
        messenger.sendTemplateMessage(template, fbUser, fbPage);
    }

    public UnoResponse getAIResponse(Agent agent, String message, String businessName, Integer businessId) throws AIException {
        Map<String, String> context = new HashMap<>();
        context.put("business-name", businessName);
        context.put("business-id", String.valueOf(businessId));
        return fromAIResult(aiService.request(agent, message, context));
    }

    public UnoResponse fromAIResult(Result result) {
        UnoResponse unoResponse = new UnoResponse();
        unoResponse.setAction(result.getAction());
        unoResponse.setScore(result.getScore());
        unoResponse.setMessage(result.getFulfillment().getSpeech());

        if (!unoResponse.getAction().equals(AgentAction.SMALLTALK_GREETINGS) && result.getContexts() != null && !result.getContexts().isEmpty()) {
            unoResponse.setParameters(
                    result.getContexts().stream().map(
                            context -> {
                                Map<String, JsonElement> contextParams = context.getParameters();
                                Map<String, Object> params = new HashMap<>();

                                Map<String, String> values = new HashMap<>();
                                contextParams.forEach((key, value) -> {
                                    if (!key.contains(".original") && value != null) {
                                        logger.info(key + " -> " + value);

                                        if (key.equals("business-name") || key.equals("business-id")) {
                                            values.put(key, value.getAsString());
                                        } else {
                                            if (value.isJsonArray()) {
                                                value.getAsJsonArray().forEach(v -> values.putAll(extractParameters(v, contextParams.get(key.concat(".original")))));
                                            } else {
                                                values.putAll(extractParameters(value, contextParams.get(key.concat(".original"))));
                                            }
                                        }
                                    }
                                });
                                params.put(context.getName(), values);
                                return params;
                            }
                    ).reduce(null, (a, b) -> b)
            );
        }

        return unoResponse;
    }

    private Map<String, String> extractParameters(JsonElement value, JsonElement originalElement) {
        Map<String, String> element = new HashMap<>();
        String original = (originalElement == null) ? null : originalElement.getAsString();
        element.put(value.getAsString(), original);
        return element;
    }

}
