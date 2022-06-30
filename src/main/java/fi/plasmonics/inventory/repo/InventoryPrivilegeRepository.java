package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryPrivilege;

@Repository("inventoryPrivilegeRepository")
public interface InventoryPrivilegeRepository extends JpaRepository<InventoryPrivilege, Long> {

    Optional<InventoryPrivilege> findByPrivilegeName(String name);
}
