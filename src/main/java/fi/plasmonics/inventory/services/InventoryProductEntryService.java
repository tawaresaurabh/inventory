package fi.plasmonics.inventory.services;

import org.springframework.stereotype.Component;

import java.util.List;

import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;

@Component
public interface InventoryProductEntryService {

     List<InventoryProductEntry> getInventoryProductEntries();
     List<InventoryProductEntry> getInventoryProductEntriesByProduct(Product product);
     InventoryProductEntry save(InventoryProductEntry inventoryProductEntry);


}
