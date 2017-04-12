package co.unobot.uno.integrations.facebook;

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

/**
 * Wrapper class for Facebook APIs. This class can be extended by services
 * that need to use Facebook APIs.
 *
 * @author Shyam Anand
 */
@PropertySource("classpath:messenger.properties")
@Component
public class Facebook {

    private static final Logger logger = LoggerFactory.getLogger(Facebook.class);

    private static String PAGE_ACCESS_TOKEN;
    private static UriComponentsBuilder MESSAGES_API;
    private static UriComponentsBuilder PAGES_API;

    @Autowired
    public Facebook(@Value("${graph.uri}") String graphUri,
                    @Value("${endpoint.messages}") String messagesApi,
                    @Value("${endpoint.pages}") String pagesApi,
                    @Value("${page.accessToken}") String pageAccessToken) throws URISyntaxException {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(new URI(graphUri));
        PAGES_API = uriBuilder.path(pagesApi);
        MESSAGES_API = UriComponentsBuilder.fromUri(new URI(graphUri)).path(messagesApi);
        PAGE_ACCESS_TOKEN = pageAccessToken;
    }

    public SendAPIResponse sendMessage(FBOutgoingMessage message) throws GraphApiFailureException {
        GraphAPIRequest<SendAPIResponse> request = new GraphAPIRequest<>(MESSAGES_API.build().encode().toUri(), PAGE_ACCESS_TOKEN, SendAPIResponse.class);
        return request.execute(message);
    }

    public SimpleGraphResponse addSubscription(String pageId) throws GraphApiFailureException {
        URI pagesUri = PAGES_API.buildAndExpand(pageId).toUri();
        logger.info("Sending subscription request - " + pagesUri.toString());
        GraphAPIRequest<SimpleGraphResponse> request = new GraphAPIRequest<>(pagesUri, PAGE_ACCESS_TOKEN, SimpleGraphResponse.class);
        return request.execute(null);
    }
}
