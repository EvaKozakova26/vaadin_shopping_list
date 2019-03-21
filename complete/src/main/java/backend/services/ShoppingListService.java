package backend.services;

import backend.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    private ShoppingListRepository shoppingListRepository;
    private ItemService itemService;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, ItemService itemService) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemService = itemService;
    }

   /* @Transactional
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        shoppingListRepository.save(shoppingList);
        return shoppingList;
    }

    public List<ShoppingList> findItemsByUserId(User user) {
        return shoppingListRepository.findAllByUSerId(user.getId());
    }

    @Transactional
    public void removeList(ShoppingList shoppingList) {
        shoppingListRepository.removeShoppingList(shoppingList);
    }*/


  /*  public ShoppingList createList(User user, List<Item> items) {
        ShoppingList shoppingList = new ShoppingList();
        List<Item> itemList = new ArrayList<>();
        shoppingList.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        shoppingList.setUser(user);
        saveShoppingList(shoppingList);
        for (Item item : items) {
            Optional<Item> dbItem = itemService.findById(item);
            if (dbItem.isPresent()) {
                dbItem.get().setShoppingList(shoppingList);
                itemService.saveItem(dbItem.get());
                itemList.add(dbItem.get());
            }
        }
        shoppingList.setItems(itemList);
        saveShoppingList(shoppingList);
        return shoppingList;
    }

    public ShoppingList updateList(ShoppingListDto shoppingListDto, User user) {
        ShoppingList shoppingList = shoppingListDto.getShoppingList();
        List<Item> itemList = new ArrayList<>();

        for (Item item : shoppingListDto.getItems()) {
            Optional<Item> dbItem = itemService.findById(item);
            if (dbItem.isPresent()) {
                dbItem.get().setShoppingList(shoppingList);
                itemService.saveItem(dbItem.get());
                itemList.add(dbItem.get());
            }
        }

        shoppingList.setItems(itemList);
        shoppingList.setUser(user);
        return saveShoppingList(shoppingList);
    }*/
}
