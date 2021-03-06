package co.unobot.uno.ai.services;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import co.unobot.uno.ai.AIException;
import co.unobot.uno.ai.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         30/03/17
 */
@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    public Result request(Agent agent, String message, Map<String, String> context) throws AIException {
        AIConfiguration aiConfiguration = new AIConfiguration(agent.getClientAccessToken());
        AIDataService aiDataService = new AIDataService(aiConfiguration);

        AIContext aiContext = new AIContext("meta-info");
        aiContext.setParameters(context);
        aiContext.setLifespan(100);

        AIRequest aiRequest = new AIRequest(message);
        aiRequest.addContext(aiContext);
        try {
            AIResponse response = aiDataService.request(aiRequest);
            if (response.getStatus().getCode() != 200)
                throw new AIException("API request failed. Response status: [" + response.getStatus().getCode() + "] " + response.getStatus().getErrorDetails());

            logger.info("[AI Response] " + response.getResult());
            return response.getResult();
        } catch (AIServiceException e) {
            logger.error(e.getMessage());
            throw new AIException(e.getMessage());
        }
    }
}
