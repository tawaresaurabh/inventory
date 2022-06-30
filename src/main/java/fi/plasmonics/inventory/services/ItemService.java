package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.repo.ItemRepository;

@Service("itemService")
public class ItemService  {

    @Autowired
    private ItemRepository itemRepository;


    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }


    public void delete(Long id) {
        itemRepository.deleteById(id);
    }


    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }


}
