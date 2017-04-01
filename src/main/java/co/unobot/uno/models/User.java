package co.unobot.uno.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by shyam on 30/03/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private List<Business> interactedBusinesses;

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

    public List<Business> getInteractedBusinesses() {
        return interactedBusinesses;
    }

    public void setInteractedBusinesses(List<Business> interactedBusinesses) {
        this.interactedBusinesses = interactedBusinesses;
    }
}
