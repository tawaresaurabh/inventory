package fi.plasmonics.inventory.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "items")
@Getter
@Setter
public class Item extends InventoryEntity {


    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;


    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;


    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ItemOrder> itemOrders = new HashSet<>();


    @Column(name = "threshold_quantity")
    private BigDecimal thresholdQuantity;


    @ElementCollection
    @CollectionTable(name = "notification_emails", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "notification_email_id")
    private List<String> notificationEmails;


}
