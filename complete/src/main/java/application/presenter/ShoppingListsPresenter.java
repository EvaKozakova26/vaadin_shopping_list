package application.presenter;

import application.model.ShoppingList;
import application.model.User;
import application.services.ShoppingListService;
import application.services.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@SpringComponent
public class ShoppingListsPresenter {

    private final ShoppingListService shoppingListService;

    private final UserService userService;

    @Autowired
    public ShoppingListsPresenter(ShoppingListService shoppingListService, UserService userService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }


    public List<ShoppingList> getAllLists() {
        return shoppingListService.findAllLists();
    }

    public List<ShoppingList> getAllListsByUser(String name) {
        Optional<User> user = userService.findByUsername(name);
        return user.map(shoppingListService::findItemsByUserId).orElse(null);
    }

    public void deletelist(ShoppingList shoppingList) {
        shoppingListService.removeList(shoppingList);
    }

}
