package co.unobot.uno.integrations.zomato.restaurant;

import co.unobot.uno.integrations.zomato.ZomatoRequestType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 08/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant extends ZomatoRequestType {
    private int id;
    private String name;
    private String url;
    private ResLocation location;
    @JsonProperty("average_cost_for_two")
    private int averageCostForTwo;

    /**
     * Price bracket of the restaurant (1 being pocket friendly and 4 being the costliest) ,
     */
    @JsonProperty("price_range")
    private int priceRange;
    private String currency;
    private String thumb;
    @JsonProperty("featured_image")
    private String featuredImage;
    @JsonProperty("photos_url")
    private String photosUrl;
    @JsonProperty("menu_url")
    private String menuUrl;

    /**
     * URL of the restaurant's events page
     */
    @JsonProperty("events_url")
    private String eventsUrl;
    @JsonProperty("user_rating")
    private UserRating userRating;
    @JsonProperty("has_online_delivery")
    private boolean hasOnlineDelivery;
    /**
     * Valid only if hasOnlineDelivery = 1; whether the restaurant is accepting online orders right now ,
     */
    @JsonProperty("is_delivering_now")
    private boolean isDeliveringNow;

    /**
     * Whether the restaurant has table reservation enabled or not ,
     */
    @JsonProperty("has_table_booking")
    private boolean hasTableBooking;

    /**
     * Short URL of the restaurant page; for use in apps or social shares ,
     */
    private String deeplink;
    private String cuisines;
    //private int all_reviews_count; (integer, optional): [Partner access] Number of reviews for the restaurant ,
    //photo_count (integer, optional): [Partner access] Total number of photos for the restaurant, at max 10 photos for partner access ,
    //phone_numbers (string, optional): [Partner access] Restaurant's contact numbers in csv format ,
    //photos (Array[Photo], optional): [Partner access] List of restaurant photos ,
    //all_reviews (Array[Review], optional): [Partner access] List of restaurant reviews

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResLocation getLocation() {
        return location;
    }

    public void setLocation(ResLocation location) {
        this.location = location;
    }

    public int getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(int averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public boolean isHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(boolean hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public boolean is_delivering_now() {
        return isDeliveringNow;
    }

    public void setDeliveringNow(boolean deliveringNow) {
        this.isDeliveringNow = deliveringNow;
    }

    public boolean isHasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(boolean hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }
}
