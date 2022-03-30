package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.repo.InventoryProductEntryRepository;

@Service("inventoryProductEntryService")
public class InventoryProductEntryServiceImpl implements InventoryProductEntryService {

    @Autowired
    private InventoryProductEntryRepository inventoryProductEntryRepository;

    @Override
    public List<InventoryProductEntry> getInventoryProductEntries() {
        return inventoryProductEntryRepository.findAll();
    }

    @Override
    public List<InventoryProductEntry> getInventoryProductEntriesByProduct(Product product) {
        return inventoryProductEntryRepository.findByProduct(product);
    }
    @Override
    public InventoryProductEntry save(InventoryProductEntry inventoryProductEntry) {
        return inventoryProductEntryRepository.save(inventoryProductEntry);
    }

}
