package co.unobot.uno.chat.services;

import co.unobot.uno.ai.AIException;
import co.unobot.uno.ai.services.AIService;
import co.unobot.uno.chat.models.IncomingMessage;
import co.unobot.uno.chat.models.UnoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 02/04/17.
 */
@Service
public class UnoService {

    private static final Logger logger = LoggerFactory.getLogger(UnoService.class);

    @Autowired
    private AIService aiService;

    public UnoResponse getResponse(IncomingMessage incomingMessage) {
        try {
            UnoResponse response = aiService.request(incomingMessage.getMessage());

            //ToDo - Logging, user profiling, etc

            logger.info("AI response - " + response.getAction() + " - " + response.getMessage());

            return response;
        } catch (AIException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("AI failure: " + e.getMessage());
        }
    }
}
