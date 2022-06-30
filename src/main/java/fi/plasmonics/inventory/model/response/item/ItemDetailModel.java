package fi.plasmonics.inventory.model.response.item;

import java.util.Set;

import fi.plasmonics.inventory.model.response.itemorder.ItemOrderModel;

public class ItemDetailModel extends ItemModel{
    private Set<ItemOrderModel> itemOrderModels;

    public Set<ItemOrderModel> getItemOrderModels() {
        return itemOrderModels;
    }

    public void setItemOrderModels(Set<ItemOrderModel> itemOrderModels) {
        this.itemOrderModels = itemOrderModels;
    }
}
