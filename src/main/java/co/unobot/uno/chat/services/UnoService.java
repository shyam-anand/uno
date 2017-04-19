package co.unobot.uno.chat.services;

import co.unobot.uno.ai.AIException;
import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.services.AIService;
import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.services.BusinessService;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.Attachment;
import co.unobot.uno.integrations.facebook.models.message.incoming.Message;
import co.unobot.uno.integrations.facebook.services.MessengerSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shyam on 02/04/17.
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

    public void messageFromFB(@NotNull Message incomingFbMessage, @NotNull FBUser fbUser, @NotNull FBPage fbPage) {

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
            UnoResponse response = getAIResponse(agent, message, business.getName(), business.getId());
            messenger.send(response.getMessage(), fbUser, fbPage);
        } catch (AIException e) {
            logger.error(e.getMessage());
        }
    }

    public UnoResponse getAIResponse(Agent agent, String message, String businessName, Integer businessId) throws AIException {
        Map<String, String> context = new HashMap<>();
        context.put("business-name", businessName);
        context.put("business-id", String.valueOf(businessId));
        return new UnoResponse().fromAIResult(aiService.request(agent, message, context));
    }

}
