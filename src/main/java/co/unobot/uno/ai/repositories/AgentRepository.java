package co.unobot.uno.ai.repositories;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.businesses.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shyam on 17/04/17.
 */
@Repository
public interface AgentRepository extends CrudRepository<Agent, Integer> {
    List<Agent> findByCategories_name(Category category);

    Agent findByName(String name);

    Agent findByNameAndCategories_name(String name, Category category);
}
