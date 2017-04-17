package co.unobot.uno.businesses.controllers;

import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.services.BusinessService;
import co.unobot.uno.commons.dto.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shyam on 17/04/17.
 */
@RestController
@RequestMapping("/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businesses;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCategories() {
        return new ResponseEntity<>(new Response(true, businesses.getCategories()), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBusiness(@RequestBody BusinessDTO business) {
        return new ResponseEntity<>(new Response(true, getDTO(businesses.create(business))), HttpStatus.OK);
    }

    private BusinessDTO getDTO(Business business) {
        return modelMapper.map(business, BusinessDTO.class);
    }
}
