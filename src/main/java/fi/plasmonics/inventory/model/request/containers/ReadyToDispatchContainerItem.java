package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;

public class ReadyToDispatchContainerItem implements Serializable {
    private final String containerId;
    private final String barcode;
    private final String productId;

    public ReadyToDispatchContainerItem(String containerId, String barcode, String productId) {
        this.containerId = containerId;
        this.barcode = barcode;
        this.productId = productId;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getProductId() {
        return productId;
    }
}
