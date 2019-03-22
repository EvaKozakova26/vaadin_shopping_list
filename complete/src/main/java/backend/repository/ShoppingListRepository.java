package backend.repository;

import backend.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    ShoppingList findById(int id);

    List<ShoppingList> findAllByUserId(int id);

}
