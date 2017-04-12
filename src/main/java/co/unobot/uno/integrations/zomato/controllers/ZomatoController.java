package co.unobot.uno.integrations.zomato.controllers;

import co.unobot.uno.integrations.zomato.ZomatoRequestFailedException;
import co.unobot.uno.integrations.zomato.services.ZomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity search(@RequestParam("q") String query,
                                 @RequestParam("city") String city) {
        try {
            return new ResponseEntity<>(zomatoService.search(query, city), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @RequestMapping(value = "/restaurant/{res_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity restaurant(@PathVariable("res_id") String restaurantId) {
        try {
            return new ResponseEntity<>(zomatoService.getRestaurant(restaurantId), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @RequestMapping(value = "/restaurant/{res_id}/dailymenu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dailyMenu(@PathVariable("res_id") String restaurantId) {
        try {
            return new ResponseEntity<>(zomatoService.getDailyMenu(restaurantId), HttpStatus.OK);
        } catch (ZomatoRequestFailedException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}
