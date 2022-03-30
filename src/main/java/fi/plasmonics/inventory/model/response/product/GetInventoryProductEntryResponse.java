package fi.plasmonics.inventory.model.response.product;

import java.io.Serializable;
import java.util.List;

public class GetInventoryProductEntryResponse implements Serializable {
    private List<InventoryProductEntryModel> inventoryProductEntryModelList;

    public List<InventoryProductEntryModel> getInventoryProductEntryItemList() {
        return inventoryProductEntryModelList;
    }

    public void setInventoryProductEntryItemList(List<InventoryProductEntryModel> inventoryProductEntryModelList) {
        this.inventoryProductEntryModelList = inventoryProductEntryModelList;
    }
}
