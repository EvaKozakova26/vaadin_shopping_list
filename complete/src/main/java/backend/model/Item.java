package backend.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "items", schema = "demo")
public class Item {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Timestamp createdAt;
	private double count;
	private boolean state;

	@ManyToOne
	private ShoppingList shoppingList;

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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}

