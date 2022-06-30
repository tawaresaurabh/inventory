package fi.plasmonics.inventory.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_privileges")
@Getter
@Setter
public class InventoryPrivilege extends InventoryEntity{

    @Column(name = "privilege_name")
    private String privilegeName;


    @ManyToMany(mappedBy = "inventoryPrivileges")
    private Collection<InventoryRole> inventoryRoles;


}
