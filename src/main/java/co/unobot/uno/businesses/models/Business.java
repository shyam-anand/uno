package co.unobot.uno.businesses.models;

import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Created by shyam on 02/04/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "business")
public class Business {

    @Id
    private String id;
    private String name;
    @JoinColumn(name = "fb_user_id")
    private FBUser fbUser;
    @JoinColumn(name = "fb_page_id")
    private FBPage fbPage;
    @JoinColumn(name = "category")
    private Category category;
    private String description;
    private String address;

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

    public FBUser getFbUser() {
        return fbUser;
    }

    public void setFbUser(FBUser fbUser) {
        this.fbUser = fbUser;
    }

    public FBPage getFbPage() {
        return fbPage;
    }

    public void setFbPage(FBPage fbPage) {
        this.fbPage = fbPage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
}