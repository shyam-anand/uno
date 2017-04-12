package co.unobot.uno.integrations.zomato;

import co.unobot.uno.integrations.zomato.dailymenu.DailyMenu;
import co.unobot.uno.integrations.zomato.restaurant.Restaurant;
import co.unobot.uno.integrations.zomato.search.Search;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shyam on 07/04/17.
 */
@PropertySource("classpath:zomato.properties")
@Component
public class Zomato {

    private final static Logger logger = LoggerFactory.getLogger(Zomato.class);

    private static String apiUrl;
    private static String apiKey;

    private enum EndPoint {
        DAILY_MENU("dailymenu"),
        RESTAURANT("restaurant"),
        SEARCH("search");

        private String name;

        EndPoint(String s) {
            this.name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Autowired
    public Zomato(@Value("${zomato.api.key}") String api_key,
                  @Value("${zomato.api.url}") String api_url) {
        apiKey = api_key;
        apiUrl = api_url;
    }

    public DailyMenu dailyMenu(String restaurantId) throws ZomatoRequestFailedException {
        ZomatoRequest<DailyMenu> zomatoRequest = new ZomatoRequest<>(DailyMenu.class, EndPoint.DAILY_MENU, restaurantId);
        return zomatoRequest.callApi();
    }

    public Restaurant restaurant(String restaurantId) throws ZomatoRequestFailedException {
        ZomatoRequest<Restaurant> zomatoRequest = new ZomatoRequest<>(Restaurant.class, EndPoint.RESTAURANT, restaurantId);
        return zomatoRequest.callApi();
    }

    public Search search(String query, City city) throws ZomatoRequestFailedException {
        ZomatoRequest<String> zomatoRequest = new ZomatoRequest<>(String.class, EndPoint.SEARCH);
        zomatoRequest.setParam("q", query);
        zomatoRequest.setParam("entity_type", "city");
        zomatoRequest.setParam("entity_id", city.value());

        String response = zomatoRequest.callApi();
        logger.info(response);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, Search.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private class ZomatoRequest<T> {

        final Class<T> responseType;
        final String endPoint;
        private Map<String, String> requestParams = new HashMap<>();

        public ZomatoRequest(Class<T> responseType, EndPoint endPoint) {
            this.responseType = responseType;
            this.endPoint = endPoint.name;
        }

        public ZomatoRequest(Class<T> responseType, EndPoint endPoint, String restaurantId) {
            this.responseType = responseType;
            this.endPoint = endPoint.name;
            setRestaurantId(restaurantId);
        }

        public void setParam(String key, String value) {
            requestParams.put(key, value);
        }

        public void setRestaurantId(String resId) {
            setParam("res_id", resId);
        }

        /**
         * Makes HTTP GET call to the Zomato API, with end point as defined by the type parameter
         *
         * @return T extends ZomatoRequestType
         */
        private T callApi() throws ZomatoRequestFailedException {

            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            requestParams.forEach(queryParams::add);

            final URI url = UriComponentsBuilder
                    .fromHttpUrl(apiUrl)
                    .path(endPoint)
                    .queryParams(queryParams)
                    .build()
                    .encode()
                    .toUri();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("user_key", apiKey);

            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            try {
                ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
                if (responseEntity.getStatusCode().is2xxSuccessful())
                    return responseEntity.getBody();
                else
                    throw new ZomatoRequestFailedException(responseEntity.getStatusCode(), "Request failed");
            } catch (HttpClientErrorException e) {
                logger.error(e.getStatusCode().value() + " " + e.getStatusCode().getReasonPhrase() + "\n" + e.getResponseBodyAsString());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ZomatoErrorResponse zomatoErrorResponse = mapper.readValue(e.getResponseBodyAsString(), ZomatoErrorResponse.class);
                    throw new ZomatoRequestFailedException(e.getStatusCode(), zomatoErrorResponse.getMessage());
                } catch (IOException ex) {
                    logger.error("Error parsing response from Zomato: " + e.getResponseBodyAsString());
                    throw new ZomatoRequestFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response from Zomato: " + e.getResponseBodyAsString());
                }
            }
        }
    }

}
