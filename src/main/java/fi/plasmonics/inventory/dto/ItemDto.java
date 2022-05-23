package fi.plasmonics.inventory.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.model.response.product.ItemModel;

public class ItemDto implements Serializable {
    private Item item;


    public ItemDto(Item item) {
        this.item = item;
    }



    public ItemModel toModel(){
        ItemModel itemModel = new ItemModel();
        itemModel.setId(String.valueOf(item.getId()));
        itemModel.setUnitOfMeasure(String.valueOf(item.getUnitOfMeasure()));
        itemModel.setDescription(item.getDescription());
        itemModel.setName(item.getName());
        if(item.getCreateTime() != null){
            itemModel.setCreateTime(item.getCreateTime().toString());
        }

        BigDecimal qty = BigDecimal.ZERO;
        for(ItemOrder itemOrder: item.getItemOrders()){
            if(itemOrder.getItemOrderType().equals(ItemOrderType.INCOMING)){
               qty =  qty.add(itemOrder.getQuantity());
            }else{
                qty =  qty.subtract(itemOrder.getQuantity());
            }
        }
        itemModel.setAvailableQuantity(qty.toString());
        itemModel.setCreatedBy("ADMIN");

        return itemModel;
    }
}
