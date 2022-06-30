package fi.plasmonics.inventory.model.request.item;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateItemOrder implements Serializable {
    private final String productId;
    private final String quantity;
    private final String action;
    private final String enteredBy;

}
