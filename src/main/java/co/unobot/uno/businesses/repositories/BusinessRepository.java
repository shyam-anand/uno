package co.unobot.uno.businesses.repositories;

import co.unobot.uno.businesses.models.Business;
import co.unobot.uno.integrations.facebook.models.FBPage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by shyam on 17/04/17.
 */
@Repository
public interface BusinessRepository extends CrudRepository<Business, Integer> {
    Business findByFbPage(FBPage fbPage);
}
