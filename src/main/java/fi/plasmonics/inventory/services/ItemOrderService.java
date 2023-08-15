package fi.plasmonics.inventory.services;

import static fi.plasmonics.inventory.common.Constants.CAUSE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.eventpublishers.EventPublisher;
import fi.plasmonics.inventory.events.CheckThresholdReachedEvent;
import fi.plasmonics.inventory.exceptions.ErrorType;
import fi.plasmonics.inventory.exceptions.InventoryException;
import fi.plasmonics.inventory.exceptions.OperationType;
import fi.plasmonics.inventory.repo.ItemOrderRepository;
import fi.plasmonics.inventory.repo.ItemRepository;

@Service("itemOrderService")
public class ItemOrderService  {


    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private ItemService itemService;
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
    public ItemOrder save(Long productId, BigDecimal quantity, ItemOrderType itemOrderType) throws InventoryException{
        ItemOrder itemOrder = new ItemOrder();
        Item item = itemService.getItemById(productId);

        BigDecimal availableQuantity = BigDecimal.ZERO;
        for(ItemOrder existingOrder: item.getItemOrders()){
            if(ItemOrderType.INCOMING.equals(existingOrder.getItemOrderType())){
                availableQuantity =  availableQuantity.add(existingOrder.getQuantity());
            }else{
                availableQuantity =  availableQuantity.subtract(existingOrder.getQuantity());
            }
        }

        if(ItemOrderType.OUTGOING.equals(itemOrderType) && availableQuantity.compareTo(quantity) <= 0) {
            throw new InventoryException(ErrorType.BAD_ARGUMENT, OperationType.CREATE)
                .addKeyValueDetail(CAUSE, "Order failed , Available quantity in inventory is: "+ availableQuantity);
        }

        itemOrder.setItem(item);
        itemOrder.setItemOrderType(itemOrderType);
        item.setCreatedBy("TEST");
        itemOrder.setCreateTime(Timestamp.from(Instant.now()));
        itemOrder.setQuantity(quantity);


        eventPublisher.publishCheckThresholdEmailEvent(new CheckThresholdReachedEvent(itemOrder.getItem().getName(), itemOrder.getItemOrderType(), itemOrder.getItem().getNotificationEmails(), itemOrder.getItem().getThresholdQuantity(), availableQuantity, itemOrder.getQuantity()));
        return itemOrderRepository.save(itemOrder);
    }





}
