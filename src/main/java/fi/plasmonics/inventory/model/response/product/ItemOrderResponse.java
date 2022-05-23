package fi.plasmonics.inventory.model.response.product;

import java.io.Serializable;
import java.util.List;

public class ItemOrderResponse implements Serializable {
    private List<ItemOrderModel> itemOrderList;

    public List<ItemOrderModel> getItemOrderList() {
        return itemOrderList;
    }

    public void setItemOrderList(List<ItemOrderModel> itemOrderList) {
        this.itemOrderList = itemOrderList;
    }
}
