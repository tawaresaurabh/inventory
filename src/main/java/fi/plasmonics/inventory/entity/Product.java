package fi.plasmonics.inventory.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;


    @Enumerated(EnumType.STRING)
    private ProductType productType;


    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;


    @Column(name = "created_time")
    private Timestamp createTime;


    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<InventoryProductEntry> inventoryProductEntries = new HashSet<>();


    @Column(name = "threshold_quantity")
    private BigDecimal thresholdQuantity;


    @Column(name = "notification_email_ids")
    private String notificationEmailIds;


    @Column(name = "send_notification_below_threshold")
    private Boolean sendNotificationBelowThreshold;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Set<InventoryProductEntry> getInventoryProductEntries() {
        return inventoryProductEntries;
    }

    public void setInventoryProductEntries(Set<InventoryProductEntry> inventoryProductEntries) {
        this.inventoryProductEntries = inventoryProductEntries;
    }


    public void addInventoryProductEntry(InventoryProductEntry inventoryProductEntry) {
        inventoryProductEntries.add(inventoryProductEntry);
        inventoryProductEntry.setProduct(this);
    }

    public void removeLocaleParameter(InventoryProductEntry inventoryProductEntry) {
        inventoryProductEntries.remove(inventoryProductEntry);
        inventoryProductEntry.setProduct(null);
    }

    public BigDecimal getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(BigDecimal thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

    public String getNotificationEmailIds() {
        return notificationEmailIds;
    }

    public void setNotificationEmailIds(String notificationEmailIds) {
        this.notificationEmailIds = notificationEmailIds;
    }

    public Boolean getSendNotificationBelowThreshold() {
        return sendNotificationBelowThreshold;
    }

    public void setSendNotificationBelowThreshold(Boolean sendNotificationBelowThreshold) {
        this.sendNotificationBelowThreshold = sendNotificationBelowThreshold;
    }
}
