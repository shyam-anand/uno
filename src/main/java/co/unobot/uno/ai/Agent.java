package co.unobot.uno.ai;

import co.unobot.uno.businesses.models.Category;

import javax.persistence.*;

/**
 * Created by shyam on 17/04/17.
 */
@Entity
@Table(name = "agent_config")
public class Agent {

    @Id
    private Integer id;
    private String name;
    @Column(name = "client_access_token")
    private String clientAccessToken;
    @Column(name = "developer_access_token")
    private String developerAccessToken;
    @ManyToOne
    @JoinTable(
            name = "category_agent",
            joinColumns = {@JoinColumn(name = "agent")},
            inverseJoinColumns = {@JoinColumn(name = "category")}
    )
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
