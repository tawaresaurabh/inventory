package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryUserAccount;

@Repository("inventoryUserAccountRepository")
public interface InventoryUserAccountRepository extends JpaRepository<InventoryUserAccount, Long> {


    Optional<InventoryUserAccount> findByUserName(String userName);

}
