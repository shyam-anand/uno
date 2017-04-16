package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.repositories.FBUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 16/04/17.
 */
@Service
public class UsersService {

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
        if (user == null) {
            user = save(fbUser);
        }
        return user;
    }

    private FBUser save(FBUser user) {
        return userRepo.save(user);
    }
}
