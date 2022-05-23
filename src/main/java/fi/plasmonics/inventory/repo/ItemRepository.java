package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.plasmonics.inventory.entity.Item;

@Repository("itemRepository")
public interface ItemRepository extends JpaRepository<Item, Long > {
}
