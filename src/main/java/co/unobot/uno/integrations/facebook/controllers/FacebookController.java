package co.unobot.uno.integrations.facebook.controllers;

import co.unobot.uno.commons.dto.Response;
import co.unobot.uno.integrations.facebook.Facebook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by shyam on 09/04/17.
 */
@Controller
@RequestMapping("/facebook")
public class FacebookController {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "index";
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity config() {
        Map<String, String> config = Facebook.getConfig();
        return new ResponseEntity<>(new Response(true, config), HttpStatus.OK);
    }
}
