package fi.plasmonics.inventory.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_roles")
@Getter
@Setter
public class InventoryRole extends InventoryEntity {


    @Column(name = "role_name")
    private String roleName;

    @OneToOne(mappedBy = "inventoryRole")
    private InventoryUserAccount inventoryUserAccount;


}
