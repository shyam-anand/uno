package co.unobot.uno.integrations.messenger;

import co.unobot.uno.integrations.messenger.models.message.outgoing.FBOutgoingMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Wrapper class for Facebook APIs. This class can be extended by services
 * that need to use Facebook APIs.
 *
 * @author Shyam Anand
 */
@PropertySource("messenger.properties")
@Component
public class Facebook {

    private static final Logger logger = LoggerFactory.getLogger(Facebook.class);

    private static String PAGE_ACCESS_TOKEN;
    private static URI MESSAGES_API;

    @Autowired
    public Facebook(@Value("${uri}") String uri, @Value("${page.accessToken}") String pageAccessToken) throws URISyntaxException {
        MESSAGES_API = new URI(uri);
        PAGE_ACCESS_TOKEN = pageAccessToken;
    }

    private class SendAPIResponse {
        @JsonProperty("recipient_id")
        private String recipientId;

        @JsonProperty("mid")
        private String mid;

        @JsonCreator
        public SendAPIResponse(@JsonProperty("recipient_id") String recipientId, @JsonProperty("mid") String mid) {
            this.recipientId = recipientId;
            this.mid = mid;
        }

        public String getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(String recipientId) {
            this.recipientId = recipientId;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }
    }

    public String sendMessage(FBOutgoingMessage message) throws FacebookApiFailedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(message, headers);

        String requestObjAsString = "<object>";
        try {
            ObjectMapper mapper = new ObjectMapper();
            requestObjAsString = mapper.writeValueAsString(requestEntity);
        } catch (JsonProcessingException e) {
            logger.warn("Unable to parse object to JSON: " + e.getMessage());
        }

        try {
            URI uri = UriComponentsBuilder.fromUri(MESSAGES_API)
                    .queryParam("access_token", PAGE_ACCESS_TOKEN)
                    .build()
                    .encode()
                    .toUri();
            logger.info("Facebook API call - POST to {} with body {}", uri.toASCIIString(), requestObjAsString);
            ResponseEntity<SendAPIResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, SendAPIResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                SendAPIResponse response = responseEntity.getBody();
                return response.getMid();
            } else {
                logger.error("API failed with status: " + responseEntity.getStatusCode().name() + " " + responseEntity.getStatusCode().getReasonPhrase());
                throw new FacebookApiFailedException(responseEntity.getStatusCode() + " " + responseEntity.getStatusCode().getReasonPhrase());
            }
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());
            throw new FacebookApiFailedException(e.getMessage());
        }
    }
}
