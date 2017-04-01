package co.unobot.uno.chat.controllers;

import co.unobot.uno.ai.AIException;
import co.unobot.uno.chat.IncomingMessage;
import co.unobot.uno.chat.UnoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.unobot.uno.ai.AIService;

/**
 * Created by shyam on 30/03/17.
 */
@RestController
public class UnoController {

    @Autowired
    private AIService aiService;

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public ResponseEntity<?> getAIResponse(@RequestBody IncomingMessage incomingMessage) {
        try {
            UnoResponse response = aiService.request(incomingMessage.getMessage());
            return new ResponseEntity<UnoResponse>(response, HttpStatus.OK);
        } catch (AIException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
