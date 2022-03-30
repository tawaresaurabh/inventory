package fi.plasmonics.inventory.model.response.container;

import java.io.Serializable;

public class ContainerModel implements Serializable {

    private String id;
    private String barcode;
    private String volume;
    private String productName;
    private String containerState;
    private String createTime;
    private String openTime;
    private String readyToDispatchTime;
    private String dispatchedTime;
    private String dispatchedBy;
    private String readyToDispatchBy;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getContainerState() {
        return containerState;
    }

    public void setContainerState(String containerState) {
        this.containerState = containerState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getReadyToDispatchTime() {
        return readyToDispatchTime;
    }

    public void setReadyToDispatchTime(String readyToDispatchTime) {
        this.readyToDispatchTime = readyToDispatchTime;
    }

    public String getDispatchedTime() {
        return dispatchedTime;
    }

    public void setDispatchedTime(String dispatchedTime) {
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
