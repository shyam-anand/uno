package co.unobot.uno.integrations.messenger;

import co.unobot.uno.integrations.messenger.models.message.outgoing.FBOutgoingMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Wrapper class for Facebook APIs. This class can be extended by services
 * that need to use Facebook APIs.
 *
 * @author Shyam Anand
 */
@PropertySource("messenger.properties")
@Component
public class Facebook {

    private static String PAGE_ACCESS_TOKEN;
    private static String MESSAGES_API;

    @Autowired
    public Facebook(@Value("${uri}") String uri, @Value("${page.accessToken}") String pageAccessToken) {
        MESSAGES_API = uri;
        PAGE_ACCESS_TOKEN = pageAccessToken;
    }

    private class SendAPIResponse {
        @JsonProperty("recipient_id")
        private String recipientId;

        @JsonProperty("mid")
        private String mid;

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

    public String sendMessage(FBOutgoingMessage message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(message, headers);

        HttpEntity<SendAPIResponse> responseEntity = restTemplate.exchange(MESSAGES_API, HttpMethod.POST, requestEntity, SendAPIResponse.class);
        SendAPIResponse response = responseEntity.getBody();
        return response.getMid();
    }
}
