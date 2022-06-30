package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryUser;
import fi.plasmonics.inventory.repo.InventoryUserRepository;

@Service("inventoryUserService")
public class InventoryUserService{


    @Autowired
    private InventoryUserRepository inventoryUserRepository;



    public InventoryUser save(InventoryUser inventoryUser) {
        return inventoryUserRepository.save(inventoryUser);
    }


    public Optional<InventoryUser> getInventoryUser(long userId) {
        return inventoryUserRepository.findById(userId);
    }
}
