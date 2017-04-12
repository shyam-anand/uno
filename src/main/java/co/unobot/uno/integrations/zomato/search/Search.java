package co.unobot.uno.integrations.zomato.search;

import co.unobot.uno.integrations.zomato.ZomatoRequestType;

import java.util.List;

/**
 * Created by shyam on 08/04/17.
 */
public class Search extends ZomatoRequestType {

    /**
     * Number of results found
     */
    private int results_found;

    /**
     * The starting location within results from which the results were fetched (used for pagination)
     */
    private int results_start;

    /**
     * The number of results fetched (used for pagination)
     */
    private int results_shown;

    private List<RestaurantList> restaurants;

    public int getResults_found() {
        return results_found;
    }

    public void setResults_found(int results_found) {
        this.results_found = results_found;
    }

    public int getResults_start() {
        return results_start;
    }

    public void setResults_start(int results_start) {
        this.results_start = results_start;
    }

    public int getResults_shown() {
        return results_shown;
    }

    public void setResults_shown(int results_shown) {
        this.results_shown = results_shown;
    }

    public List<RestaurantList> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantList> restaurants) {
        this.restaurants = restaurants;
    }
}
