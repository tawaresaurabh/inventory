package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;

@Repository("inventoryProductEntryRepository")
public interface InventoryProductEntryRepository extends JpaRepository<InventoryProductEntry, Long> {

    List<InventoryProductEntry> findByProduct(Product product);
}
