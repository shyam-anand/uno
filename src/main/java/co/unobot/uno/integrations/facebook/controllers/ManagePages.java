package co.unobot.uno.integrations.facebook.controllers;

import co.unobot.uno.integrations.facebook.controllers.dto.Response;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import co.unobot.uno.integrations.facebook.services.PagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shyam on 09/04/17.
 */
@RestController
@RequestMapping("/facebook/pages")
public class ManagePages {

    @Autowired
    private PagesService pages;

    @RequestMapping(value = "/{pageId}", method = RequestMethod.POST)
    public ResponseEntity addPage(@PathVariable("pageId") String pageId) {
        try {
            Response response = new Response();
            response.setStatus(pages.addPage(pageId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (GraphApiFailureException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }
}
