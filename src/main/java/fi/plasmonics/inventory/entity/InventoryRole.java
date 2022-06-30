package fi.plasmonics.inventory.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_roles")
@Getter
@Setter
public class InventoryRole extends InventoryEntity {


    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "inventoryRoles")
    private Collection<InventoryUserAccount> inventoryUserAccounts;

    @ManyToMany
    @JoinTable(
            name = "inventory_roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<InventoryPrivilege> inventoryPrivileges = new HashSet<>();


}
