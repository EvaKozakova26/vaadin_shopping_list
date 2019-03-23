package application.presenter;

import application.model.Item;
import application.services.ItemService;
import application.services.ShoppingListService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CurrentShoppingListPresenter {

    private final ItemService itemService;
    private final ShoppingListService shoppingListService;

    @Autowired
    public CurrentShoppingListPresenter(ItemService itemService, ShoppingListService shoppingListService) {
        this.itemService = itemService;
        this.shoppingListService = shoppingListService;
    }

    public void onListUpdateListener(int shoppingListID, List<Item> items) {
        shoppingListService.updateList(shoppingListService.findShopListById(shoppingListID), items, null);
    }

    public List<Item> getItemsByList(int shoppingListID) {
        return itemService.findItems(shoppingListID);
    }
}
