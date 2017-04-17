package co.unobot.uno.ai;

import co.unobot.uno.businesses.models.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shyam on 17/04/17.
 */
@Entity
@Table(name = "agent_config")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Agent {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;

    @Column(name = "client_access_token")
    @JsonProperty("client_access_token")
    private String clientAccessToken;

    @Column(name = "developer_access_token")
    @JsonProperty("developer_access_token")
    private String developerAccessToken;

    @ManyToMany
    @JoinTable(name = "category_agent", joinColumns = {@JoinColumn(name = "agent")}, inverseJoinColumns = {@JoinColumn(name = "category")})
    private List<Category> categories;

    public Integer getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientAccessToken() {
        return clientAccessToken;
    }

    public void setClientAccessToken(String clientAccessToken) {
        this.clientAccessToken = clientAccessToken;
    }

    public String getDeveloperAccessToken() {
        return developerAccessToken;
    }

    public void setDeveloperAccessToken(String developerAccessToken) {
        this.developerAccessToken = developerAccessToken;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
