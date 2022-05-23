package fi.plasmonics.inventory.model.request.product;

import java.io.Serializable;

public class CreateItemOrder implements Serializable {
    private final String productId;
    private final String quantity;
    private final String action;
    private final String enteredBy;

    public CreateItemOrder(String productId, String quantity, String action, String enteredBy) {
        this.productId = productId;
        this.quantity = quantity;
        this.action = action;
        this.enteredBy = enteredBy;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAction() {
        return action;
    }

    public String getEnteredBy() {
        return enteredBy;
    }
}
