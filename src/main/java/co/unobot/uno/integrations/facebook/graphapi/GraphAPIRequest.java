package co.unobot.uno.integrations.facebook.graphapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * Wrapper for Facebook Graph APIs
 * Created by shyam on 09/04/17.
 */
public class GraphAPIRequest<T> {

    private static Logger logger = LoggerFactory.getLogger(GraphAPIRequest.class);

    private URI api;
    private String pageAccessToken;
    private final Class<T> responseType;

    public GraphAPIRequest(URI uri, String pageAccessToken, Class<T> responseType) {
        this.api = uri;
        this.pageAccessToken = pageAccessToken;
        this.responseType = responseType;
    }

    public T execute(Object requestObject) throws GraphApiFailureException, GraphAPIError {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity;
        if (requestObject == null)
            requestEntity = new HttpEntity<>(headers);
        else
            requestEntity = new HttpEntity<>(requestObject, headers);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestObjAsString = mapper.writeValueAsString(requestEntity);

            URI uri = UriComponentsBuilder.fromUri(api)
                    .queryParam("access_token", pageAccessToken)
                    .build()
                    .encode()
                    .toUri();
            logger.info("Facebook API call - POST to {} with body {}", uri.toASCIIString(), requestObjAsString);

            ResponseEntity<T> responseEntity = post(uri, requestEntity);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                logger.error("API failed with status: " + responseEntity.getStatusCode().name() + " " + responseEntity.getStatusCode().getReasonPhrase());
                throw new GraphApiFailureException(responseEntity.getStatusCode(), responseEntity.getStatusCode().getReasonPhrase());
            }
        } catch (HttpClientErrorException e) {
            try {
                logger.error(e.getMessage() + ": " + e.getResponseBodyAsString());

                String responseString = e.getResponseBodyAsString();
                ObjectMapper mapper = new ObjectMapper();
                GraphAPIError graphAPIError = mapper.readValue(responseString, GraphAPIError.class);
                throw graphAPIError;
            } catch (IOException ex) {
                throw new GraphApiFailureException(e.getStatusCode(), e.getMessage());
            }
        } catch (JsonProcessingException e) {
            logger.warn("Unable to parse object to JSON: " + e.getMessage());
            throw new GraphApiFailureException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ResponseEntity<T> post(URI uri, HttpEntity requestEntity) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri, HttpMethod.POST, requestEntity, responseType);
    }
}
