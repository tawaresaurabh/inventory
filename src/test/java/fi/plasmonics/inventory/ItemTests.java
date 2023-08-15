package fi.plasmonics.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fi.plasmonics.inventory.controllers.ItemsController;
import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.model.response.item.ItemModel;
import fi.plasmonics.inventory.model.response.itemorder.ItemOrderResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemTests  {
//
//    @InjectMocks
//    ItemsController itemsController;
//
//    private Item item;
//    private Set<ItemOrder> itemOrders;
//    List<Item> itemList;
//
//
//    @BeforeEach
//    void setup() {
//        item = createItem();
//        itemOrders = new HashSet<>();
//        itemOrders.add(createIncomingItemOrder());
//        itemOrders.add(createOutgoingItemOrder());
//        item.setItemOrders(itemOrders);
//        itemList = new ArrayList<>();
//        itemList.add(createItem());
//
//    }
//
//
//    @Test
//    void checkGetItemById(){
//        when(itemService.getItemById(ITEM_ID)).thenReturn(item);
//        ResponseEntity<ItemModel> itemModelResponseEntity = itemsController.getItemById(String.valueOf(ITEM_ID));
//        assertEquals(ITEM_ID.toString(), itemModelResponseEntity.getBody().getId());
//
//    }
//
//    @Test
//    void checkGetProductsItems(){
//        when(itemService.getItems()).thenReturn(itemList);
//        ResponseEntity<List<ItemModel>> itemsModelResponseEntity = itemsController.getItems();
//        assertEquals(1, itemsModelResponseEntity.getBody().size());
//    }
//
//    @Test
//    void checkGetItemOrder(){
//        when(itemService.getItemById(ITEM_ID)).thenReturn(item);
//        ResponseEntity<ItemOrderResponse> itemOrderResponseEntity = itemsController.getItemOrders(String.valueOf(ITEM_ID));
//        assertEquals(2, itemOrderResponseEntity.getBody().getItemOrderList().size());
//    }




}
