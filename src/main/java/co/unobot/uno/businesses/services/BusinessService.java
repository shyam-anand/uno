package co.unobot.uno.businesses.services;

import co.unobot.uno.businesses.models.Category;
import co.unobot.uno.businesses.repositories.BusinessRepository;
import co.unobot.uno.businesses.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shyam on 17/04/17.
 */
@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businesses;

    @Autowired
    private CategoryRepository categories;

    public List<Category> getCategories() {
        return categories.findAll();
    }
}
