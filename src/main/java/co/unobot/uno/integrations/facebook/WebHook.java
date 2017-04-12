package co.unobot.uno.integrations.facebook;

import co.unobot.uno.integrations.facebook.models.message.incoming.FBIncomingMessage;
import co.unobot.uno.integrations.facebook.services.MessengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MessengerService messengerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity webHookPost(HttpServletRequest request,
                                      @RequestParam(value = "hub.mode") String hubMode,
                                      @RequestParam(value = "hub.challenge") String hubChallenge) {
        logger.info(request.getMethod() + " - " + request.getQueryString());
        return new ResponseEntity<>(hubChallenge, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity webHook(@RequestBody FBIncomingMessage fbMessage) {
        logger.info("POST - " + fbMessage);

        messengerService.receive(fbMessage);

        return new ResponseEntity<>(fbMessage, HttpStatus.OK);
    }
}
