package fi.plasmonics.inventory.events;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class ThresholdReachedEmailEvent {


    private final String itemName;
    private final List<String> notificationEmailIds;
    private final BigDecimal thresholdQuantity;
    private final BigDecimal remainingQuantity;

}
