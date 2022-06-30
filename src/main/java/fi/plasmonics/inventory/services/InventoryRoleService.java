package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryRole;
import fi.plasmonics.inventory.repo.InventoryRoleRepository;

@Service("inventoryRoleService")
public class InventoryRoleService {

    @Autowired
    private InventoryRoleRepository inventoryRoleRepository;


    public InventoryRole save(InventoryRole inventoryRole) {
        return inventoryRoleRepository.save(inventoryRole);
    }


    public Optional<InventoryRole> getRoleByName(String name){
        return inventoryRoleRepository.findByRoleName(name);
    }
}
