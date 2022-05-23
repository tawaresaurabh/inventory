package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.repo.ItemRepository;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    @Transactional
    public Item save(Item item) {
        Item item1 = itemRepository.save(item);
        return item1;
    }


    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).get();
    }


}
