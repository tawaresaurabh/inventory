package fi.plasmonics.inventory.dto;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.model.response.product.ItemOrderModel;


public class ItemOrderDto {

    private ItemOrder itemOrder;

    public ItemOrderDto(ItemOrder itemOrder) {
        this.itemOrder = itemOrder;
    }


    public ItemOrderModel toModel(){
        ItemOrderModel itemOrderModel = new ItemOrderModel();
        itemOrderModel.setId(String.valueOf(itemOrder.getId()));
        itemOrderModel.setAction(itemOrder.getItemOrderType().toString());
        itemOrderModel.setQuantity(itemOrder.getQuantity().toString());
        itemOrderModel.setCreateTime(itemOrder.getCreateTime().toString());
        itemOrderModel.setEnteredBy(itemOrder.getCreatedBy());
        return itemOrderModel;
    }
}
