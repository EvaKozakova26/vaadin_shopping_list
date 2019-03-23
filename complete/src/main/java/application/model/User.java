package application.model;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Indexed
@Table(name = "users", schema = "demo")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;
    @NotNull
    private String password;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShoppingList> shoppingLists;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }
}
