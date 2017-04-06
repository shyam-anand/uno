package co.unobot.uno.integrations.messenger;

import co.unobot.uno.integrations.messenger.models.message.outgoing.FBOutgoingMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Wrapper class for Facebook APIs. This class can be extended by services
 * that need to use Facebook APIs.
 *
 * @author Shyam Anand
 */

public abstract class Facebook {

    private static String PAGE_ACCESS_TOKEN;
    private static URI MESSAGES_API;

    public Facebook(String messagesApi, String pageAccessToken) throws URISyntaxException {
        MESSAGES_API = new URI(messagesApi);
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
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        HttpEntity<SendAPIResponse> responseEntity = restTemplate.exchange(MESSAGES_API, HttpMethod.POST, requestEntity, SendAPIResponse.class);
        SendAPIResponse response = responseEntity.getBody();
        return response.getMid();
    }
}
