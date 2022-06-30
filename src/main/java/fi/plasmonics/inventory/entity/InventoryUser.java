package fi.plasmonics.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_user")
@Getter
@Setter
public class InventoryUser extends InventoryEntity{

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_user_account_id", referencedColumnName = "id")
    private InventoryUserAccount inventoryUserAccount;







}
