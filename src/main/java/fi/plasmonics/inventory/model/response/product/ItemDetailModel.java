package fi.plasmonics.inventory.model.response.product;

import java.util.Set;

public class ItemDetailModel extends ItemModel{
    private Set<ItemOrderModel> itemOrderModels;

    public Set<ItemOrderModel> getItemOrderModels() {
        return itemOrderModels;
    }

    public void setItemOrderModels(Set<ItemOrderModel> itemOrderModels) {
        this.itemOrderModels = itemOrderModels;
    }
}
