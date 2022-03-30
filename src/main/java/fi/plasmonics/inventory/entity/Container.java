package fi.plasmonics.inventory.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="container")
public class Container {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContainerState containerState;


    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "barcode")
    private String barcode;


    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "create_time")
    private Timestamp createTime;


    @Column(name = "open_time")
    private Timestamp openTime;


    @Column(name = "ready_to_dispatch_time")
    private Timestamp readyToDispatchTime;

    @Column(name = "dispatched_time")
    private Timestamp dispatchedTime;


    @Column(name = "dispatched_by")
    private String dispatchedBy;


    @Column(name = "ready_to_dispatch_by")
    private String readyToDispatchBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContainerState getContainerState() {
        return containerState;
    }

    public void setContainerState(ContainerState containerState) {
        this.containerState = containerState;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public Timestamp getReadyToDispatchTime() {
        return readyToDispatchTime;
    }

    public void setReadyToDispatchTime(Timestamp readyToDispatchTime) {
        this.readyToDispatchTime = readyToDispatchTime;
    }

    public Timestamp getDispatchedTime() {
        return dispatchedTime;
    }

    public void setDispatchedTime(Timestamp dispatchedTime) {
        this.dispatchedTime = dispatchedTime;
    }

    public String getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(String dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public String getReadyToDispatchBy() {
        return readyToDispatchBy;
    }

    public void setReadyToDispatchBy(String readyToDispatchBy) {
        this.readyToDispatchBy = readyToDispatchBy;
    }
}
