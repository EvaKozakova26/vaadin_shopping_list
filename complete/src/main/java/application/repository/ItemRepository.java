package application.repository;

import application.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(String lastName);

	Item findById(int id);

	List<Item> findAllByShoppingListId(int id);

}
