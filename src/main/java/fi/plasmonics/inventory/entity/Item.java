package fi.plasmonics.inventory.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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

    @Column(name = "item_purchase_link")
    private String itemPurchaseLink;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Lob
    @Column(name = "item_image")
    private byte[] itemImage;


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
