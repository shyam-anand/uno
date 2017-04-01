package co.unobot.uno.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 30/03/17.
 */
public class User {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
