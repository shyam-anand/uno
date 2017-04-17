package co.unobot.uno.businesses.repositories;

import co.unobot.uno.businesses.models.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by shyam on 17/04/17.
 */
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();

    Category findByName(String category);
}
