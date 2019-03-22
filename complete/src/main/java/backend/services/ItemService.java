package backend.services;

import backend.model.Item;
import backend.model.ShoppingList;
import backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    private EntityManager em;


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

    @Transactional
    public List<Item> findItems(int id) {
        return itemRepository.findAllByShoppingListId(id);
    }


   /* public Optional<Item> findItem(Item item) {
        javax.persistence.Query query = this.em.createQuery("SELECT i FROM Item i WHERE i.name =:name " +
                "AND i.createdAt = :time AND i.count = :countI AND i.state =:state ");
        query.setParameter("name", item.getName());
        query.setParameter("time", item.getCreatedAt());
        query.setParameter("countI", item.getCount());
        query.setParameter("state", item.getState());
        return (Optional<Item>) query.setMaxResults(1).getResultList().stream().findFirst();
    }*/
}
