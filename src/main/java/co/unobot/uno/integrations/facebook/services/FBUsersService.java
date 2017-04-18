package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.repositories.FBUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shyam on 16/04/17.
 */
@Service
public class FBUsersService {

    @Autowired
    private FBUserRepo userRepo;

    public FBUser get(String id) {
        return userRepo.findOne(id);
    }

    public FBUser create(FBUser user) {
        return save(user);
    }

    public FBUser login(FBUser fbUser) {
        FBUser user = userRepo.findOne(fbUser.getId());
        if (user == null || user.getId() == null) {
            user = new FBUser();
            user.setId(fbUser.getId());
        }
        user.setName(fbUser.getName());
        return save(user);
    }

    private FBUser save(FBUser user) {
        return userRepo.save(user);
    }

    public List<Business> getBusinesses(String userId) {
        FBUser user = userRepo.findOne(userId);
        return user.getBusinesses();
    }
}
