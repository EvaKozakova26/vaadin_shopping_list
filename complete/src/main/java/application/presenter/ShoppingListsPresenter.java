package application.presenter;

import application.model.ShoppingList;
import application.services.ShoppingListService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class ShoppingListsPresenter {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListsPresenter(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    public List<ShoppingList> getAllLists() {
        return shoppingListService.findAllLists();
    }

}