package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.ai.Agent;
import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.businesses.services.BusinessService;
import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.repositories.FBPagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shyam on 09/04/17.
 */
@Service
public class FBPagesService {

    @Autowired
    private Facebook facebook;

    @Autowired
    private FBPagesRepo pagesRepo;

    @Autowired
    private BusinessService businessService;

    public FBPage get(String id) {
        return pagesRepo.findOne(id);
    }

    public void subscribe(FBPage page) {
        save(page);
    }

    private void save(FBPage page) {
        pagesRepo.save(page);
    }

    public List<String> subscriptions(String userId) {
        List<FBPage> pages = pagesRepo.findByUserId(userId);
        return pages.stream().map(FBPage::getId).collect(Collectors.toList());
    }

    public Agent getConnectedAgent(String pageId) throws EntityNotFoundException {
        Business business = businessService.getForFBPage(pageId);
        if (business == null)
            return null;
        return business.getAgent();
    }

    public FBPage renewAccessToken(FBPage page) {
        facebook.getAccessToken(page.getId());
        return null;
    }
}
