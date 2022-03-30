package fi.plasmonics.inventory.model.request.containers;

import java.io.Serializable;

public class ContainerItem implements Serializable {
    private final String productId;
    private final String volume;
    private final String quantity;


    public ContainerItem(String productId, String volume, String quantity) {
        this.productId = productId;
        this.volume = volume;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getVolume() {
        return volume;
    }

    public String getQuantity() {
        return quantity;
    }
}
