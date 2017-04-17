package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.repositories.FBPagesRepo;
import co.unobot.uno.integrations.facebook.repositories.FBUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shyam on 09/04/17.
 */
@Service
public class PagesService {

    @Autowired
    private Facebook facebook;

    @Autowired
    private FBPagesRepo pagesRepo;

    @Autowired
    private FBUserRepo userRepo;

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

    public FBPage renewAccessToken(FBPage page) {
        facebook.getAccessToken(page.getId());
        return null;
    }
}
