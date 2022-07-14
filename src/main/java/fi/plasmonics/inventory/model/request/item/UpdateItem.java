package fi.plasmonics.inventory.model.request.item;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class UpdateItem implements Serializable {

    private final String id;
    private final String name;
    private final String productType;
    private final String unitOfMeasure;
    private final String description;

}
