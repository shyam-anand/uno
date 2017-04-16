package co.unobot.uno.integrations.facebook.controllers;

import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.services.PagesService;
import co.unobot.uno.models.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shyam on 09/04/17.
 */
@RestController
@RequestMapping("/facebook/pages")
public class ManagePages {

    @Autowired
    private PagesService pages;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscribe(@RequestBody FBPage page) {

        pages.subscribe(page);
        return new ResponseEntity<>(new Response(true, "Subscribed to " + page.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscriptions(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(new Response(true, pages.subscriptions(userId)), HttpStatus.OK);
    }
}
