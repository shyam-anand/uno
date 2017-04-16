package co.unobot.uno.integrations.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by shyam on 16/04/17.
 */
@Entity
@Table(name = "fb_pages")
public class FBPage {

    @Id
    @Column(name = "page_id")
    private String id;
    private String name;
    @JsonProperty("access_token")
    private String accessToken;

    @ManyToOne
    @JoinTable(
            name = "fb_users_pages",
            joinColumns = {@JoinColumn(name = "page_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private FBUser user;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public FBUser getUser() {
        return user;
    }

    public void setUser(FBUser user) {
        this.user = user;
    }
}
