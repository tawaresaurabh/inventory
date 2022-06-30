package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.eventpublishers.EventPublisher;
import fi.plasmonics.inventory.events.CheckThresholdReachedEvent;
import fi.plasmonics.inventory.repo.ItemOrderRepository;

@Service("itemOrderService")
public class ItemOrderService  {

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    @Autowired
    private EventPublisher eventPublisher;



    public List<ItemOrder> getItemOrders() {
        return itemOrderRepository.findAll();
    }


    public List<ItemOrder> getItemOrdersByItem(Item item) {
        return itemOrderRepository.findByItem(item);
    }


    @Transactional
    public ItemOrder save(ItemOrder itemOrder, BigDecimal availableQuantity) {
        eventPublisher.publishCheckThresholdEmailEvent(new CheckThresholdReachedEvent(itemOrder.getItem().getName(), itemOrder.getItemOrderType(), itemOrder.getItem().getNotificationEmails(), itemOrder.getItem().getThresholdQuantity(), availableQuantity, itemOrder.getQuantity()));
        return itemOrderRepository.save(itemOrder);
    }

}
