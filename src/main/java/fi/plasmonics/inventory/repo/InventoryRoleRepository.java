package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryRole;

@Repository("inventoryRoleRepository")
public interface InventoryRoleRepository extends JpaRepository<InventoryRole, Long> {
    Optional<InventoryRole> findByRoleName(String name);
}
