package co.unobot.uno.integrations.facebook.repositories;

import co.unobot.uno.integrations.facebook.models.FBUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by shyam on 16/04/17.
 */
@Repository
public interface FBUserRepo extends CrudRepository<FBUser, String> {
}
