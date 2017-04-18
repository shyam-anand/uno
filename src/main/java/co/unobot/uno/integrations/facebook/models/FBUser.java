package co.unobot.uno.integrations.facebook.models;

import co.unobot.uno.businesses.models.Business;
import com.fasterxml.jackson.annotation.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shyam on 02/04/17.
 */
@Entity
@Table(name = "fb_users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FBUser implements Persistable<String> {

    @Id
    @Column(name = "user_id")
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "fb_users_pages", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "page_id")})
    private List<FBPage> pages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fbUser")
    private List<Business> businesses;

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

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
