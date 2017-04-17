package co.unobot.uno.services;

import co.unobot.uno.businesses.models.Category;
import co.unobot.uno.businesses.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 17/04/17.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category get(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    public Category get(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
