package co.unobot.uno.integrations.zomato.dailymenu;

import co.unobot.uno.integrations.zomato.ZomatoRequestType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shyam on 07/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyMenu extends ZomatoRequestType {

    @JsonProperty("daily_menu")
    List<DailyMenuCategory> dailyMenuCategoryList;

    public List<DailyMenuCategory> getDailyMenuCategoryList() {
        return dailyMenuCategoryList;
    }

    public void setDailyMenuCategoryList(List<DailyMenuCategory> dailyMenuCategoryList) {
        this.dailyMenuCategoryList = dailyMenuCategoryList;
    }
}
