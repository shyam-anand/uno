package co.unobot.uno.integrations.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shyam on 02/04/17.
 */
@RestController
@RequestMapping("/messenger")
public class WebHook {

    private static final Logger logger = LoggerFactory.getLogger(WebHook.class);

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity webHookPost(HttpServletRequest request) {
        logger.info(request.getMethod() + " - " + request.getQueryString());
        return new ResponseEntity(HttpStatus.OK);
    }
}
