package co.unobot.uno.businesses.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shyam on 17/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessDTO {

    private int id;
    private String name;
    private String category;
    private String description;
    private String address;
    @JsonProperty("fb_page_id")
    private String fbPageId;
    @JsonProperty("fb_user_id")
    private String fbUserId;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFbPageId() {
        return fbPageId;
    }

    public void setFbPageId(String fbPageId) {
        this.fbPageId = fbPageId;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }
}
