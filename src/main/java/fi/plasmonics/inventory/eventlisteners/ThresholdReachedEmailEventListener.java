package fi.plasmonics.inventory.eventlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;


import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.eventpublishers.EventPublisher;
import fi.plasmonics.inventory.events.CheckThresholdReachedEvent;
import fi.plasmonics.inventory.events.ThresholdReachedEmailEvent;
import fi.plasmonics.inventory.services.EmailService;

@Component
public class ThresholdReachedEmailEventListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EventPublisher eventPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAfterCommitThresholdReached(ThresholdReachedEmailEvent thresholdReachedEmailEvent) {
        String[] to = new String[thresholdReachedEmailEvent.getNotificationEmailIds().size()];
        for (int i = 0; i < thresholdReachedEmailEvent.getNotificationEmailIds().size(); i++) {
            to[i] = thresholdReachedEmailEvent.getNotificationEmailIds().get(i);
        }
        emailService.sendSimpleMessage("Threshold alert", "Threshold reached for item " + thresholdReachedEmailEvent.getItemName() + "Threshold set at " + thresholdReachedEmailEvent.getThresholdQuantity() + "Current Available Qty " + thresholdReachedEmailEvent.getRemainingQuantity(), to);

    }


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void checkIfThresholdReached(CheckThresholdReachedEvent checkThresholdReachedEvent) {
        if (checkThresholdReachedEvent.getItemOrderType().equals(ItemOrderType.OUTGOING) && !checkThresholdReachedEvent.getNotificationEmailIds().isEmpty()) {
            BigDecimal remainingQtyAfterOrder = checkThresholdReachedEvent.getAvailableQuantity().subtract(checkThresholdReachedEvent.getItemOrderQuantity());
            if (checkThresholdReachedEvent.getThresholdQuantity().compareTo(remainingQtyAfterOrder) > 0) {
                eventPublisher.publishSendThresholdEmailEvent(new ThresholdReachedEmailEvent(checkThresholdReachedEvent.getItemName(), checkThresholdReachedEvent.getNotificationEmailIds(), checkThresholdReachedEvent.getThresholdQuantity(), remainingQtyAfterOrder));
            }
        }
    }

}
