package co.unobot.uno.integrations.facebook.controllers;

import co.unobot.uno.commons.dto.Response;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.services.FBUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shyam on 16/04/17.
 */
@RestController
@RequestMapping("/facebook/users")
public class ManageUsers {

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
}
