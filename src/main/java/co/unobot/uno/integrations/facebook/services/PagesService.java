package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 09/04/17.
 */
@Service
public class PagesService {

    @Autowired
    private Facebook facebook;

    public boolean addPage(String pageId) throws GraphApiFailureException {
        return facebook.addSubscription(pageId).isSuccess();
    }
}
