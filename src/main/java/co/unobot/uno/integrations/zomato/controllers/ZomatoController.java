package co.unobot.uno.integrations.zomato.controllers;

import co.unobot.uno.integrations.zomato.ZomatoRequestFailedException;
import co.unobot.uno.integrations.zomato.services.ZomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shyam on 09/04/17.
 */
@RestController
@RequestMapping("zomato")
public class ZomatoController {

    @Autowired
    private ZomatoService zomatoService;

    @RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity search(@RequestParam("q") String query) {
        try {
            return new ResponseEntity<>(zomatoService.search(query), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @RequestMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity restaurant(@RequestParam("res_id") String restaurantId) {
        try {
            return new ResponseEntity<>(zomatoService.getRestaurant(restaurantId), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @RequestMapping(value = "/dailymenu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dailyMenu(@RequestParam("res_id") String restaurantId) {
        try {
            return new ResponseEntity<>(zomatoService.getDailyMenu(restaurantId), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}
