package co.unobot.uno.businesses.models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shyam on 17/04/17.
 */
@Entity
public class Category {

    @Id
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
