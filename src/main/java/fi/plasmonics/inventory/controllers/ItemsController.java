package fi.plasmonics.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fi.plasmonics.inventory.dto.ItemDetailDto;
import fi.plasmonics.inventory.dto.ItemDto;
import fi.plasmonics.inventory.dto.ItemOrderDto;
import fi.plasmonics.inventory.entity.InventoryEntity;
import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.entity.UnitOfMeasure;
import fi.plasmonics.inventory.eventpublishers.EventPublisher;
import fi.plasmonics.inventory.model.request.product.CreateItem;
import fi.plasmonics.inventory.model.request.product.CreateItemOrder;
import fi.plasmonics.inventory.model.request.product.UpdateItem;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.model.response.product.DeleteItemResponse;
import fi.plasmonics.inventory.model.response.product.ItemDetailModel;
import fi.plasmonics.inventory.model.response.product.ItemModel;
import fi.plasmonics.inventory.model.response.product.ItemOrderActionResponse;
import fi.plasmonics.inventory.model.response.product.ItemOrderModel;
import fi.plasmonics.inventory.model.response.product.ItemOrderResponse;
import fi.plasmonics.inventory.services.ItemOrderService;
import fi.plasmonics.inventory.services.ItemService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ItemsController {

    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemOrderService itemOrderService;

    @Autowired
    private EventPublisher eventPublisher;


    @GetMapping("/items")
    public List<ItemModel> getItems() {
        return itemService.getItems().stream()
                .map(item -> new ItemDto(item).toModel())
                .collect(Collectors.toList());
    }


    @GetMapping("/itemOrders/{itemId}")
    public ItemOrderResponse getItemOrders(@PathVariable String itemId) {
        ItemOrderResponse itemOrderResponse = new ItemOrderResponse();
        Item item = itemService.getItemById(Long.parseLong(itemId));
        List<ItemOrderModel> itemOrderModelList = item.getItemOrders().stream()
                .sorted(Comparator.comparing(InventoryEntity::getCreateTime).reversed())
                .map(inventoryProductEntry -> new ItemOrderDto(inventoryProductEntry).toModel())
                .collect(Collectors.toList());
        itemOrderResponse.setItemOrderList(itemOrderModelList);
        return itemOrderResponse;
    }


    @PutMapping("/items")
    public Item updateItem(@RequestBody UpdateItem updateItem) {
        Item item = itemService.getItemById(Long.parseLong(updateItem.getId()));
        item.setName(updateItem.getName());
        item.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class, updateItem.getUnitOfMeasure()));
        item.setDescription(updateItem.getDescription());
        return itemService.save(item);
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody CreateItem createItem) {
        Item item = new Item();
        item.setName(createItem.getName());
        item.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class, createItem.getUnitOfMeasure()));
        item.setDescription(createItem.getDescription());
        item.setCreateTime(Timestamp.from(Instant.now()));
        item.setThresholdQuantity(new BigDecimal(createItem.getThresholdQuantity()));
        item.setNotificationEmails(createItem.getNotificationEmails());
        return itemService.save(item);
    }


    @PostMapping("/itemOrder")
    public ItemOrderActionResponse saveItemOrder(@RequestBody CreateItemOrder createItemOrder) {
        ItemOrder itemOrder = new ItemOrder();
        Item item = itemService.getItemById(Long.parseLong(createItemOrder.getProductId()));
        ItemModel itemModel = new ItemDto(item).toModel();
        BigDecimal availableQuantity = new BigDecimal(itemModel.getAvailableQuantity());
        BigDecimal outGoingQuantity = new BigDecimal(createItemOrder.getQuantity());
        ItemOrderType itemOrderType = Enum.valueOf(ItemOrderType.class, createItemOrder.getAction());

        if (itemOrderType.equals(ItemOrderType.INCOMING) || availableQuantity.compareTo(outGoingQuantity) >= 0) {
            itemOrder.setItem(item);
            itemOrder.setItemOrderType(itemOrderType);
            itemOrder.setCreatedBy(createItemOrder.getEnteredBy());
            itemOrder.setCreateTime(Timestamp.from(Instant.now()));
            itemOrder.setQuantity(new BigDecimal(createItemOrder.getQuantity()));
            itemOrderService.save(itemOrder,availableQuantity);
            return new ItemOrderActionResponse(MessageType.SUCCESS, "Inventory updated");
        } else {
            return new ItemOrderActionResponse(MessageType.FAILURE, "Not enough units available, Please check available units");
        }

    }


    @DeleteMapping("/items/{id}")
    public DeleteItemResponse deleteItem(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        itemService.delete(itemId);
        return new DeleteItemResponse(MessageType.SUCCESS, "Item deleted successfully");
    }


    @GetMapping("/items/{id}")
    public ItemModel getItemById(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        Item item = itemService.getItemById(itemId);
        return new ItemDto(item).toModel();
    }


    @GetMapping("/items/details/{id}")
    public ItemDetailModel getItemDetailsById(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        Item item = itemService.getItemById(itemId);
        return new ItemDetailDto(item).toModel();
    }


}