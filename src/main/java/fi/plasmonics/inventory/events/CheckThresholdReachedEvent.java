package fi.plasmonics.inventory.events;

import java.math.BigDecimal;
import java.util.List;

import fi.plasmonics.inventory.entity.ItemOrderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class CheckThresholdReachedEvent {

    private final String itemName;
    private final ItemOrderType itemOrderType;
    private final List<String> notificationEmailIds;
    private final BigDecimal thresholdQuantity;
    private final BigDecimal availableQuantity;
    private final BigDecimal itemOrderQuantity;


}
