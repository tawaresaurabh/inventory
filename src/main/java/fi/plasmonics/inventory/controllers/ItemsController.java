package fi.plasmonics.inventory.controllers;

import fi.plasmonics.inventory.authentication.InventoryUserDetails;
import fi.plasmonics.inventory.configuration.OpenApiConfig;
import fi.plasmonics.inventory.dto.ItemDetailDto;
import fi.plasmonics.inventory.dto.ItemDto;
import fi.plasmonics.inventory.dto.ItemOrderDto;
import fi.plasmonics.inventory.entity.*;
import fi.plasmonics.inventory.exceptions.ItemNotFoundException;
import fi.plasmonics.inventory.model.request.item.CreateItem;
import fi.plasmonics.inventory.model.request.item.CreateItemOrder;
import fi.plasmonics.inventory.model.request.item.UpdateItem;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.model.response.item.DeleteItemResponse;
import fi.plasmonics.inventory.model.response.item.ItemDetailModel;
import fi.plasmonics.inventory.model.response.item.ItemModel;
import fi.plasmonics.inventory.model.response.itemorder.ItemOrderActionResponse;
import fi.plasmonics.inventory.model.response.itemorder.ItemOrderModel;
import fi.plasmonics.inventory.model.response.itemorder.ItemOrderResponse;
import fi.plasmonics.inventory.services.ItemOrderService;
import fi.plasmonics.inventory.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ItemsController {

    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemOrderService itemOrderService;



    @GetMapping(path = "/items",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(summary = "List Items", tags = OpenApiConfig.TAG_ITEMS)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ItemModel.class)))}),
        @ApiResponse(responseCode = "404", description = "NotFound", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public List<ItemModel> getItems() {
        return itemService.getItems().stream()
                .map(item -> new ItemDto(item).toModel())
                .collect(Collectors.toList());
    }


    @GetMapping(path = "/itemOrders/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(summary = "Get Item orders by item Id", tags = OpenApiConfig.TAG_ITEM_ORDER)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "NetworkElement", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemOrderResponse.class))}),
        @ApiResponse(responseCode = "404", description = "NotFound", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ItemOrderResponse getItemOrders(@PathVariable String itemId) {
        ItemOrderResponse itemOrderResponse = new ItemOrderResponse();
        Optional<Item> itemOptional = itemService.getItemById(Long.parseLong(itemId));
        if(itemOptional.isPresent()){
            Item item = itemOptional.get();
            List<ItemOrderModel> itemOrderModelList = item.getItemOrders().stream()
                    .sorted(Comparator.comparing(InventoryEntity::getCreateTime).reversed())
                    .map(inventoryProductEntry -> new ItemOrderDto(inventoryProductEntry).toModel())
                    .collect(Collectors.toList());
            itemOrderResponse.setItemOrderList(itemOrderModelList);
        }
        return itemOrderResponse;
    }


    @PutMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public Item updateItem(@RequestBody UpdateItem updateItem) {
        Optional<Item> itemOptional = itemService.getItemById(Long.parseLong(updateItem.getId()));
        if(itemOptional.isPresent()){
            Item item = itemOptional.get();
            item.setName(updateItem.getName());
            item.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class, updateItem.getUnitOfMeasure()));
            item.setDescription(updateItem.getDescription());
            return itemService.save(item);
        }
        throw new ItemNotFoundException();
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public Item saveItem(@RequestBody CreateItem createItem) {
        Item item = new Item();
        item.setName(createItem.getName());
        item.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class, createItem.getUnitOfMeasure()));
        item.setDescription(createItem.getDescription());
        item.setCreateTime(Timestamp.from(Instant.now()));
        item.setThresholdQuantity(new BigDecimal(createItem.getThresholdQuantity()));
        item.setNotificationEmails(createItem.getNotificationEmails());
        item.setItemPurchaseLink(createItem.getItemPurchaseLink());
        if(StringUtils.isEmpty(createItem.getUnitPrice())){
            item.setUnitPrice(BigDecimal.ZERO);
        }else{
            item.setUnitPrice(new BigDecimal(createItem.getUnitPrice()));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        InventoryUserDetails inventoryUserDetails = (InventoryUserDetails)authentication.getPrincipal();
        item.setCreatedBy(inventoryUserDetails.getUsername());
        return itemService.save(item);
    }


    @PostMapping("/itemOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ItemOrderActionResponse saveItemOrder(@RequestBody CreateItemOrder createItemOrder) {
        ItemOrder itemOrder = new ItemOrder();
        Optional<Item> itemOptional = itemService.getItemById(Long.parseLong(createItemOrder.getProductId()));
        if(itemOptional.isPresent()){
            Item item = itemOptional.get();
            ItemModel itemModel = new ItemDto(item).toModel();
            BigDecimal availableQuantity = new BigDecimal(itemModel.getAvailableQuantity());
            BigDecimal outGoingQuantity = new BigDecimal(createItemOrder.getQuantity());
            ItemOrderType itemOrderType = Enum.valueOf(ItemOrderType.class, createItemOrder.getAction());

            if (itemOrderType.equals(ItemOrderType.INCOMING) || availableQuantity.compareTo(outGoingQuantity) >= 0) {
                itemOrder.setItem(item);
                itemOrder.setItemOrderType(itemOrderType);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                InventoryUserDetails inventoryUserDetails = (InventoryUserDetails)authentication.getPrincipal();
                item.setCreatedBy(inventoryUserDetails.getUsername());
                itemOrder.setCreateTime(Timestamp.from(Instant.now()));
                itemOrder.setQuantity(new BigDecimal(createItemOrder.getQuantity()));
                itemOrderService.save(itemOrder,availableQuantity);
                return new ItemOrderActionResponse(MessageType.SUCCESS, "Inventory updated");
            } else {
                return new ItemOrderActionResponse(MessageType.FAILURE, "Not enough units available, Please check available units");
            }
        }
        throw new ItemNotFoundException();
    }


    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public DeleteItemResponse deleteItem(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        itemService.delete(itemId);
        return new DeleteItemResponse(MessageType.SUCCESS, "Item deleted successfully");
    }


    @GetMapping("/items/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ItemModel getItemById(@PathVariable String id) {
        Optional<Item> itemOptional = itemService.getItemById(Long.parseLong(id));
        if(itemOptional.isPresent()){
            return new ItemDto(itemOptional.get()).toModel();
        }else{
            throw new ItemNotFoundException();
        }
    }


    @GetMapping("/items/details/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ItemDetailModel getItemDetailsById(@PathVariable String id) {
        Optional<Item> itemOptional = itemService.getItemById(Long.parseLong(id));
        if(itemOptional.isPresent()){
            return new ItemDetailDto(itemOptional.get()).toModel();
        }else{
            throw new ItemNotFoundException();
        }
    }


}
