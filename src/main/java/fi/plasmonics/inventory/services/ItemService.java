package fi.plasmonics.inventory.services;

import java.util.List;

import fi.plasmonics.inventory.entity.Item;


public interface ItemService {

     List<Item> getItems();
     Item save(Item item);
     void delete(Long id);
     Item getItemById(Long id);
}
