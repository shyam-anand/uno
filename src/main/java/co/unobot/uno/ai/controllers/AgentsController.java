package co.unobot.uno.ai.controllers;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.services.AgentService;
import co.unobot.uno.commons.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shyam on 17/04/17.
 */
@RestController
@RequestMapping("/ai/agents")
public class AgentsController {

    @Autowired
    private AgentService agents;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Agent agent) {
        return new ResponseEntity<>(new Response(true, agents.add(agent)), HttpStatus.OK);
    }
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMultiple(@RequestBody List<Agent> agentList) {
        return new ResponseEntity<>(new Response(true, agents.addAll(agentList)), HttpStatus.OK);
    }



    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "category", required = false) String category) {
        return new ResponseEntity<>(new Response(true, agents.get(name, category)), HttpStatus.OK);

    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Agent agent) {
        return new ResponseEntity<>(new Response(true, agents.update(agent)), HttpStatus.OK);
    }
}
