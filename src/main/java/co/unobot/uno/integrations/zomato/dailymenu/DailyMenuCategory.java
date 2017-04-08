package co.unobot.uno.integrations.zomato.dailymenu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shyam on 07/04/17.
 */
public class DailyMenuCategory {

    /**
     * Restaurant id
     */
    @JsonProperty("id")
    private int resId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("dishes")
    private List<DailyMenutItem> dishes;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<DailyMenutItem> getDishes() {
        return dishes;
    }

    public void setDishes(List<DailyMenutItem> dishes) {
        this.dishes = dishes;
    }
}
