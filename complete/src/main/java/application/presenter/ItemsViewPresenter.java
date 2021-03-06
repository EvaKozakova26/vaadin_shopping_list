package application.presenter;

import application.model.Item;
import application.model.User;
import application.services.ItemService;
import application.services.ShoppingListService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemsViewPresenter {

    private final ItemService itemService;
    private final ShoppingListService shoppingListService;

    @Autowired
    public ItemsViewPresenter(ItemService itemService, ShoppingListService shoppingListService) {
        this.itemService = itemService;
        this.shoppingListService = shoppingListService;
    }

    public void saveShoppingList(User user, List<Item> items) {
        shoppingListService.createList(user, items);
    }

    public void deleteItem(Item item) {
        itemService.deleteItem(item);
    }

    public void saveItem(Item item) {
        itemService.saveItem(item);
    }

}
