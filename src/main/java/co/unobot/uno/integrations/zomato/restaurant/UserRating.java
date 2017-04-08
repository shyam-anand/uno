package co.unobot.uno.integrations.zomato.restaurant;

/**
 * Created by shyam on 08/04/17.
 */
public class UserRating {
    /**
     * Restaurant rating on a scale of 0.0 to 5.0 in increments of 0.1 ,
     */
    private double aggregate_rating;

    /**
     * Short description of the rating ,
     */
    private String rating_text;

    /**
     * Color hex code used with the rating on Zomato ,
     */
    private String rating_color;

    /**
     * Number of ratings received
     */
    private int votes;

    public double getAggregate_rating() {
        return aggregate_rating;
    }

    public void setAggregate_rating(double aggregate_rating) {
        this.aggregate_rating = aggregate_rating;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public String getRating_color() {
        return rating_color;
    }

    public void setRating_color(String rating_color) {
        this.rating_color = rating_color;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
