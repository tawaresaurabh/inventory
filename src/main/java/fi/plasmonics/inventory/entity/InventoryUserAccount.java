package fi.plasmonics.inventory.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_user_accounts")
@Getter
@Setter
public class InventoryUserAccount extends InventoryEntity {
    @Column(name = "password")
    private String password;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;
    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired;
    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired;
    @Column(name = "is_enabled")
    private boolean isEnabled;



    @ManyToMany
    @JoinTable(
            name = "inventory_users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<InventoryRole> inventoryRoles;

    @OneToOne(mappedBy = "inventoryUserAccount")
    private InventoryUser inventoryUser;


}
