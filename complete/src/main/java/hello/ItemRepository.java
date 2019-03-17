package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByName(String lastName);

	Item findById(int id);
}
