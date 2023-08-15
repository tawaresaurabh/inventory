package fi.plasmonics.inventory.controllers;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.plasmonics.inventory.configuration.OpenApiConfig;
import fi.plasmonics.inventory.dto.ItemDetailDto;
import fi.plasmonics.inventory.dto.ItemDto;
import fi.plasmonics.inventory.dto.ItemOrderDto;
import fi.plasmonics.inventory.entity.InventoryEntity;
import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.entity.UnitOfMeasure;
import fi.plasmonics.inventory.model.request.item.CreateItem;
import fi.plasmonics.inventory.model.request.item.CreateItemOrder;
import fi.plasmonics.inventory.model.request.item.UpdateItem;
import fi.plasmonics.inventory.model.response.item.ItemDetailModel;
import fi.plasmonics.inventory.model.response.item.ItemModel;
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
import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class ItemsController {

    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemOrderService itemOrderService;


    private static final String DELETED = "Deleted";

    @GetMapping(path = "/items",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(summary = "List Items", tags = OpenApiConfig.TAG_ITEMS)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ItemModel.class)))}),
        @ApiResponse(responseCode = "404", description = "NotFound", content = @Content)
    })
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public  ResponseEntity<List<ItemModel>> getItems() {
        List<ItemModel> itemModels =  itemService.getItems().stream()
                .map(item -> new ItemDto(item).toModel())
                .collect(Collectors.toList());

        return new ResponseEntity<>(itemModels, HttpStatus.OK);
    }


    @GetMapping(path = "/itemOrders/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(summary = "Get Item orders by item Id", tags = OpenApiConfig.TAG_ITEM_ORDER)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "NetworkElement", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemOrderResponse.class))}),
        @ApiResponse(responseCode = "404", description = "NotFound", content = @Content)
    })
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<ItemOrderResponse> getItemOrders(@PathVariable String itemId) {
        ItemOrderResponse itemOrderResponse = new ItemOrderResponse();
        Item item = itemService.getItemById(Long.parseLong(itemId));
        List<ItemOrderModel> itemOrderModelList = item.getItemOrders().stream()
            .sorted(Comparator.comparing(InventoryEntity::getCreateTime).reversed())
            .map(inventoryProductEntry -> new ItemOrderDto(inventoryProductEntry).toModel())
            .collect(Collectors.toList());
        itemOrderResponse.setItemOrderList(itemOrderModelList);
        return new ResponseEntity<>(itemOrderResponse, HttpStatus.OK);
    }


    @PutMapping("/items")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> updateItem(@RequestBody UpdateItem updateItem) {
        Item item = itemService.getItemById(Long.parseLong(updateItem.getId()));
        item.setName(updateItem.getName());
        item.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class, updateItem.getUnitOfMeasure()));
        item.setDescription(updateItem.getDescription());
        return new ResponseEntity<>(HttpStatus.ACCEPTED.getReasonPhrase(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/items")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> saveItem(@RequestBody CreateItem createItem) {
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
        item.setCreatedBy("TEST");
        URI location =
            fromPath("/items")
                .build()
                .toUri();
        itemService.save(item);
        return ResponseEntity.created(location).body(HttpStatus.CREATED.getReasonPhrase());
    }


    @PostMapping(value = "/itemOrder", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> saveItemOrder(@RequestBody CreateItemOrder createItemOrder) {
        BigDecimal outGoingQuantity = new BigDecimal(createItemOrder.getQuantity());
        ItemOrderType itemOrderType = Enum.valueOf(ItemOrderType.class, createItemOrder.getAction());
        itemOrderService.save(Long.parseLong(createItemOrder.getProductId()), outGoingQuantity, itemOrderType);
        URI location =
            fromPath("/itemOrder")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(HttpStatus.CREATED.getReasonPhrase());
    }


    @DeleteMapping("/items/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        Long itemId = Long.parseLong(id);
        itemService.delete(itemId);
        return new ResponseEntity<>(DELETED, HttpStatus.OK);
    }


    @GetMapping("/items/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<ItemModel> getItemById(@PathVariable String id) {
        Item item = itemService.getItemById(Long.parseLong(id));
        return new ResponseEntity<>(new ItemDto(item).toModel(), HttpStatus.OK) ;
    }


    @GetMapping("/items/details/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_VIEWER')")
    public ResponseEntity<ItemDetailModel> getItemDetailsById(@PathVariable String id) {
        Item item = itemService.getItemById(Long.parseLong(id));
        return new ResponseEntity<>(new ItemDetailDto(item).toModel(), HttpStatus.OK) ;
    }


}
