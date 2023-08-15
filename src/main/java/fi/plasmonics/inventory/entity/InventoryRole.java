package fi.plasmonics.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


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
