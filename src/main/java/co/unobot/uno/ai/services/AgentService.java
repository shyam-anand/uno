package co.unobot.uno.ai.services;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.repositories.AgentRepository;
import co.unobot.uno.businesses.models.Category;
import co.unobot.uno.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shyam on 17/04/17.
 */
@Service
public class AgentService {

    @Autowired
    private AgentRepository agents;

    @Autowired
    private CategoryService categories;

    public List<Agent> getAll() {
        return (List<Agent>) agents.findAll();
    }

    public Agent get(int id) {
        return agents.findOne(id);
    }

    public List<Agent> get(String name, String categoryName) {
        Category category = categories.get(categoryName);
        if (category != null || name != null) {
            if (category == null) {
                return Collections.singletonList(agents.findByName(name));
            } else if (name == null) {
                return agents.findByCategories_name(category);
            } else {
                return Collections.singletonList(agents.findByNameAndCategories_name(name, category));
            }
        } else {
            return getAll();
        }
    }

    public Agent add(Agent agent) {
        return agents.save(agent);
    }

    public Agent update(Agent agent) {
        Agent a = agents.findOne(agent.getId());
        if (a == null) {
            throw new EntityNotFoundException("Could not find agent with id " + agent.getId());
        }
        if (agent.getCategories() != null) {
            a.setCategories(agent.getCategories());
        }
        if (agent.getClientAccessToken() != null) {
            a.setClientAccessToken(agent.getClientAccessToken());
        }
        if (agent.getDeveloperAccessToken() != null) {
            a.setDeveloperAccessToken(agent.getDeveloperAccessToken());
        }
        if (agent.getName() != null) {
            a.setName(agent.getName());
        }
        if (agent.getDescription() != null) {
            a.setDescription(agent.getDescription());
        }
        return agents.save(a);
    }

    public List<Agent> addAll(List<Agent> agentList) {
        return agentList.stream().map(agents::save).collect(Collectors.toList());
    }
}
