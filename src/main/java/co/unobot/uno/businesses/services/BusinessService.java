package co.unobot.uno.businesses.services;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.ai.services.AgentService;
import co.unobot.uno.businesses.controllers.BusinessDTO;
import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.models.Category;
import co.unobot.uno.businesses.repositories.BusinessRepository;
import co.unobot.uno.businesses.repositories.CategoryRepository;
import co.unobot.uno.integrations.facebook.services.FBPagesService;
import co.unobot.uno.integrations.facebook.services.FBUsersService;
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

    @Autowired
    private FBPagesService fbPages;

    @Autowired
    private FBUsersService fbUsers;

    @Autowired
    private AgentService agents;

    public List<Category> getCategories() {
        return categories.findAll();
    }

    public Business create(BusinessDTO businessData) {
        Business business = new Business();
        business.setName(businessData.getName());
        business.setCategory(categories.findByName(businessData.getCategory()));
        business.setDescription(businessData.getDescription());
        business.setAddress(businessData.getAddress());
        business.setFbPage(fbPages.get(businessData.getFbPageId()));
        business.setFbUser(fbUsers.get(businessData.getFbUserId()));
        return save(business);
    }

    public Business getForFBPage(String fbPageId) {
        return businesses.findByFbPage(fbPages.get(fbPageId));
    }

    private Business save(Business business) {
        try {
            return businesses.save(business);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Business> getAll() {
        return (List<Business>) businesses.findAll();
    }

    public Business connect(int businessId, Agent agent) {
        agent = agents.get(agent.getId());
        Business business = businesses.findOne(businessId);
        business.setAgent(agent);
        return save(business);
    }

    public Business get(int businessId) {
        return businesses.findOne(businessId);
    }
}
