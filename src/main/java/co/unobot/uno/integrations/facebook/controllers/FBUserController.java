package co.unobot.uno.integrations.facebook.controllers;

import co.unobot.uno.commons.dto.Response;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.services.FBUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shyam on 16/04/17.
 */
@RestController
@RequestMapping("/facebook/users")
public class FBUserController {

    @Autowired
    private FBUsersService users;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody FBUser fbUser) {
        return new ResponseEntity<>(new Response(true, users.create(fbUser)), HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody FBUser fbUser) {
        return new ResponseEntity<>(new Response(true, users.login(fbUser)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/businesses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBusinesses(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(users.getBusinesses(userId), HttpStatus.OK);
    }
}
