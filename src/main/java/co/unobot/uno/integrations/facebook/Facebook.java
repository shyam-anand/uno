package co.unobot.uno.integrations.facebook;

import co.unobot.uno.integrations.facebook.graphapi.GraphAPIError;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIRequest;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import co.unobot.uno.integrations.facebook.graphapi.models.SendAPIResponse;
import co.unobot.uno.integrations.facebook.graphapi.models.SimpleGraphResponse;
import co.unobot.uno.integrations.facebook.models.message.outgoing.FBOutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for Facebook APIs.
 *
 * @author Shyam Anand
 */
@PropertySource("classpath:messenger.properties")
@Component
public class Facebook {

    private static final Logger logger = LoggerFactory.getLogger(Facebook.class);

    private static String APP_ID;
    private static String VERSION;
    private static UriComponentsBuilder MESSAGES_API;
    private static UriComponentsBuilder PAGES_API;

    @Autowired
    public Facebook(@Value("${app.id}") String appId,
                    @Value("${api.version}") String apiVersion,
                    @Value("${fb.graph.uri}") String graphUri,
                    @Value("${fb.endpoint.messages}") String messagesApi,
                    @Value("${fb.endpoint.pages}") String pagesApi) throws URISyntaxException {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(new URI(graphUri));
        APP_ID = appId;
        VERSION = apiVersion;
        PAGES_API = uriBuilder.path(pagesApi);
        MESSAGES_API = UriComponentsBuilder.fromUri(new URI(graphUri)).path(messagesApi);
    }

    public static Map<String, String> getConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("appId", APP_ID);
        config.put("version", VERSION);

        return config;
    }

    public SendAPIResponse sendMessage(FBOutgoingMessage message, String pageAccessToken) throws GraphApiFailureException, GraphAPIError {
        GraphAPIRequest<SendAPIResponse> request = new GraphAPIRequest<>(MESSAGES_API.build().encode().toUri(), pageAccessToken, SendAPIResponse.class);
        return request.execute(message);
    }

    public SimpleGraphResponse addSubscription(String pageId, String accessToken) throws GraphApiFailureException, GraphAPIError {
        URI pagesUri = PAGES_API.buildAndExpand(pageId).toUri();
        logger.info("Sending subscription request - " + pagesUri.toString());
        GraphAPIRequest<SimpleGraphResponse> request = new GraphAPIRequest<>(pagesUri, accessToken, SimpleGraphResponse.class);
        return request.execute(null);
    }

    public String getAccessToken(String pageId) {
        return null;
    }
}
