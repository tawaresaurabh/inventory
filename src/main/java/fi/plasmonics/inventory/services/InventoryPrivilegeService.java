package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import fi.plasmonics.inventory.entity.InventoryPrivilege;
import fi.plasmonics.inventory.repo.InventoryPrivilegeRepository;

@Service("inventoryPrivilegeService")
public class InventoryPrivilegeService {

    @Autowired
    private InventoryPrivilegeRepository inventoryPrivilegeRepository;


    public InventoryPrivilege save(InventoryPrivilege inventoryPrivilege) {
        return inventoryPrivilegeRepository.save(inventoryPrivilege);
    }


    public Optional<InventoryPrivilege> getPrivilegeByName(String name){
        return inventoryPrivilegeRepository.findByPrivilegeName(name);
    }

}
