package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.plasmonics.inventory.entity.InventoryUser;

@Repository("inventoryUserRepository")
public interface InventoryUserRepository extends JpaRepository<InventoryUser, Long> {
}
