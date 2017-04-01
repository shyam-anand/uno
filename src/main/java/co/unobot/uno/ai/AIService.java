package co.unobot.uno.ai;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import co.unobot.uno.models.UnoResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 30/03/17.
 */
@Service
public class AIService {

    private AIDataService aiDataService;

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    @Autowired
    public AIService(@Value("${api.key}") String apiKey) {
        AIConfiguration aiConfiguration = new AIConfiguration(apiKey);
        aiDataService = new AIDataService(aiConfiguration);
    }

    public UnoResponse request(String message) throws AIException {
        AIRequest aiRequest = new AIRequest(message);
        try {
            AIResponse response = aiDataService.request(aiRequest);
            if (response.getStatus().getCode() == 200) {

                Gson gson = new Gson();
                logger.info("AI response - " + gson.toJson(response.getResult()));

                return new UnoResponse(response.getResult());
            } else {
                throw new AIException("API request failed. Response status: " + response.getStatus().getCode() + " - " + response.getStatus().getErrorDetails());
            }
        } catch (AIServiceException e) {
            logger.error(e.getMessage());
            throw new AIException(e.getMessage());
        }
    }
}
