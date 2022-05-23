package fi.plasmonics.inventory.services;

import java.math.BigDecimal;
import java.util.List;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;



public interface ItemOrderService {

     List<ItemOrder> getItemOrders();
     List<ItemOrder> getItemOrdersByItem(Item item);
     ItemOrder save(ItemOrder itemOrder, BigDecimal availableQuantity);


}
