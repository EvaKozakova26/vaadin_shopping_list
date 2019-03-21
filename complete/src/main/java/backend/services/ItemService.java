package backend.services;

import backend.model.Item;
import backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    public Item findById(int id) {
        return itemRepository.findById(id);
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }
}
