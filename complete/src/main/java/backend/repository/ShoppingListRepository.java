package backend.repository;

import backend.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    Optional<ShoppingList> findById(int id);

    List<ShoppingList> findAllByUserId(int id);

}
