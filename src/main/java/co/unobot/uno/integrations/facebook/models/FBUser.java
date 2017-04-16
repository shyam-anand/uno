package co.unobot.uno.integrations.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shyam on 02/04/17.
 */
@Entity
@Table(name = "fb_users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FBUser {

    @Id
    @Column(name = "user_id")
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonIgnore
    @OneToMany
    @JoinTable(
            name = "fb_users_pages",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "page_id")}
    )
    private List<FBPage> pages;

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

    public List<FBPage> getPages() {
        return pages;
    }

    public void setPages(List<FBPage> pages) {
        this.pages = pages;
    }
}
