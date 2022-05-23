package fi.plasmonics.inventory.eventpublishers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import fi.plasmonics.inventory.events.CheckThresholdReachedEvent;
import fi.plasmonics.inventory.events.ThresholdReachedEmailEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishCheckThresholdEmailEvent(CheckThresholdReachedEvent checkThresholdReachedEvent) {
        publisher.publishEvent(checkThresholdReachedEvent);
    }

    public void publishSendThresholdEmailEvent(ThresholdReachedEmailEvent thresholdReachedEmailEvent) {
        publisher.publishEvent(thresholdReachedEmailEvent);
    }

}
