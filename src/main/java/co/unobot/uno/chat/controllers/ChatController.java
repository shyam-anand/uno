package co.unobot.uno.chat.controllers;

import co.unobot.uno.ai.AIException;
import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.services.BusinessService;
import co.unobot.uno.chat.models.IncomingMessage;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.chat.services.UnoService;
import co.unobot.uno.commons.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shyam on 30/03/17.
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UnoService uno;

    @Autowired
    private BusinessService businesses;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity getAIResponse(@RequestBody IncomingMessage incomingMessage) {
        try {
            Business business = businesses.get(incomingMessage.getBusiness().getId());
            UnoResponse response = uno.getAIResponse(business.getAgent(), incomingMessage.getMessage(), business.getName(), business.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AIException e) {
            return new ResponseEntity<>(new Response(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
