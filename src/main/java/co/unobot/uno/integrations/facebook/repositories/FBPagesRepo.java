package co.unobot.uno.integrations.facebook.repositories;

import co.unobot.uno.integrations.facebook.models.FBPage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shyam on 16/04/17.
 */
@Repository
public interface FBPagesRepo extends CrudRepository<FBPage, String> {
    List<FBPage> findByUserId(String userId);
}
