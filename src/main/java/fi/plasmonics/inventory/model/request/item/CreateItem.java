package fi.plasmonics.inventory.model.request.item;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class CreateItem implements Serializable {
    private final String name;
    private final String unitOfMeasure;
    private final String description;
    private final String thresholdQuantity;
    private final String itemPurchaseLink;
    private final String unitPrice;
    private final List<String> notificationEmails;

}
