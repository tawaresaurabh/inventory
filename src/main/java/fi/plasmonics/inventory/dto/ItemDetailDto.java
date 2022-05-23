package fi.plasmonics.inventory.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.model.response.product.ItemDetailModel;
import fi.plasmonics.inventory.model.response.product.ItemOrderModel;

public class ItemDetailDto implements Serializable {
    private Item item;

    public ItemDetailDto(Item item) {
        this.item = item;
    }


    public ItemDetailModel toModel(){
        ItemDetailModel itemDetailModel = new ItemDetailModel();
        itemDetailModel.setId(String.valueOf(item.getId()));
        itemDetailModel.setUnitOfMeasure(String.valueOf(item.getUnitOfMeasure()));
        itemDetailModel.setDescription(item.getDescription());
        itemDetailModel.setName(item.getName());
        if(item.getCreateTime() != null){
            itemDetailModel.setCreateTime(item.getCreateTime().toString());
        }

        BigDecimal qty = BigDecimal.ZERO;
        for(ItemOrder itemOrder: item.getItemOrders()){
            if(itemOrder.getItemOrderType().equals(ItemOrderType.INCOMING)){
                qty =  qty.add(itemOrder.getQuantity());
            }else{
                qty =  qty.subtract(itemOrder.getQuantity());
            }
        }
        itemDetailModel.setAvailableQuantity(qty.toString());
        itemDetailModel.setCreatedBy("ADMIN");

        Set<ItemOrderModel> itemOrderModelSet= new HashSet<>();
        item.getItemOrders().forEach(itemOrder -> {
            ItemOrderModel itemOrderModel = new ItemOrderDto(itemOrder).toModel();
            itemOrderModelSet.add(itemOrderModel);
        });

        itemDetailModel.setItemOrderModels(itemOrderModelSet);
        return itemDetailModel;


    }





}
