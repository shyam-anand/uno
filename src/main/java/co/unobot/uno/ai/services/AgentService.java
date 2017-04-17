package co.unobot.uno.ai.services;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.repositories.AgentRepository;
import co.unobot.uno.businesses.models.Category;
import co.unobot.uno.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 17/04/17.
 */
@Service
public class AgentService {

    @Autowired
    private AgentRepository agents;

    @Autowired
    private CategoryService categories;

    public Agent getAgent(Category category) {
        return agents.findByCategory(category);
    }
}
