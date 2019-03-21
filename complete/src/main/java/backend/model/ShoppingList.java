package backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Indexed
@Table(name = "shopping_lists", schema = "demo")
public class ShoppingList {

    @Id
    @GeneratedValue
    private int id;

    private Timestamp createdAt;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
    private List<Item> items;

    @ManyToOne
    @JsonIgnore
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
