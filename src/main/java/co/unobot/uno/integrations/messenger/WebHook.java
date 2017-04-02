package co.unobot.uno.integrations.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shyam on 02/04/17.
 */
@RestController
@RequestMapping("/messenger")
public class WebHook {

    private static final Logger logger = LoggerFactory.getLogger(WebHook.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity webHookPost(HttpServletRequest request,
                                      @RequestParam(value = "hub.mode") String hubMode,
                                      @RequestParam(value = "hub.challenge") String hubChallenge) {
        logger.info(request.getMethod() + " - " + request.getQueryString());
        return new ResponseEntity<>(hubChallenge, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity webHook(@RequestBody String requestBody) {
        logger.info("POST - " + requestBody);
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
