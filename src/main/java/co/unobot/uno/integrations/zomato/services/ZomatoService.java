package co.unobot.uno.integrations.zomato.services;

import co.unobot.uno.integrations.zomato.Zomato;
import co.unobot.uno.integrations.zomato.ZomatoRequestFailedException;
import co.unobot.uno.integrations.zomato.dailymenu.DailyMenu;
import co.unobot.uno.integrations.zomato.restaurant.Restaurant;
import co.unobot.uno.integrations.zomato.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 09/04/17.
 */
@Service
public class ZomatoService {

    private static final Logger logger = LoggerFactory.getLogger(ZomatoService.class);

    @Autowired
    private Zomato zomato;

    public Search search(String query) throws ZomatoRequestFailedException {
        return zomato.search(query);
    }

    public Restaurant getRestaurant(String restaurantId) throws ZomatoRequestFailedException {
        return zomato.restaurant(restaurantId);
    }

    public DailyMenu getDailyMenu(String restaurantId) throws ZomatoRequestFailedException {
        return zomato.dailyMenu(restaurantId);
    }
}
