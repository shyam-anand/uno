package co.unobot.uno.integrations.zomato.dailymenu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 07/04/17.
 */
public class DailyMenutItem {

    @JsonProperty("dish_id")
    private int dishId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private String price;

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
