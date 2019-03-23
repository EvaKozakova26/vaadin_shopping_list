package application.services;

import application.model.Item;
import application.model.ShoppingList;
import application.model.User;
import application.repository.ItemRepository;
import application.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingListService {

    private ShoppingListRepository shoppingListRepository;
    private ItemService itemService;
    private ItemRepository itemRepository;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, ItemService itemService, ItemRepository itemRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        shoppingListRepository.save(shoppingList);
        return shoppingList;
    }

    public List<ShoppingList> findItemsByUserId(User user) {
        return shoppingListRepository.findAllByUserId(user.getId());
    }

    public ShoppingList findShopListById(int id) {
        return shoppingListRepository.findById(id);
    }

    @Transactional
    public void removeList(ShoppingList shoppingList) {
        shoppingListRepository.delete(shoppingList);
    }

    public List<ShoppingList> findAllLists() {
        return shoppingListRepository.findAll();
    }


    public ShoppingList createList(User user, List<Item> items) {
        items.forEach(item -> itemService.saveItem(item));
        ShoppingList shoppingList = new ShoppingList();
        List<Item> itemList = new ArrayList<>();
        shoppingList.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        shoppingList.setUser(user);
        saveShoppingList(shoppingList);
        updateItems(shoppingList, items, itemList);
        shoppingList.setItems(itemList);
        saveShoppingList(shoppingList);
        return shoppingList;
    }


    public ShoppingList updateList(ShoppingList updatedShoppingList, List<Item> items, User user) {
        List<Item> itemList = new ArrayList<>();

        items.forEach(item -> {
            if (item.getId() == 0) {
                itemService.saveItem(item);
            }
        });

        updateItems(updatedShoppingList, items, itemList);

        updatedShoppingList.setItems(itemList);
        updatedShoppingList.setUser(user);
        return saveShoppingList(updatedShoppingList);
    }

    private void updateItems(ShoppingList updatedShoppingList, List<Item> items, List<Item> itemList) {
        for (Item item : items) {
            Item dbItem = itemService.findById(item.getId());
            dbItem.setShoppingList(updatedShoppingList);
            itemService.saveItem(dbItem);
            itemList.add(dbItem);

        }
    }
}
