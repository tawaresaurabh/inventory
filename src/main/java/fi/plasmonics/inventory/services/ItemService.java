package fi.plasmonics.inventory.services;

import static fi.plasmonics.inventory.common.Constants.CAUSE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.exceptions.ErrorType;
import fi.plasmonics.inventory.exceptions.InventoryException;
import fi.plasmonics.inventory.exceptions.OperationType;
import fi.plasmonics.inventory.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;

@Service("itemService")
@Slf4j
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


    public Item getItemById(Long id) throws InventoryException {
        return itemRepository.findById(id).orElseThrow(() -> {
            log.warn("Not found Item with id {}.", id);
            return new InventoryException(ErrorType.NOT_FOUND, OperationType.FIND)
                .addKeyValueDetail(CAUSE, "Item with id = "+id +" not found");
        });
    }

}
