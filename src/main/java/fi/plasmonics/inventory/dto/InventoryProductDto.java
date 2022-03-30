package fi.plasmonics.inventory.dto;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.model.response.product.InventoryProductEntryModel;


public class InventoryProductDto {

    private InventoryProductEntry inventoryProductEntry;

    public InventoryProductDto(InventoryProductEntry inventoryProductEntry) {
        this.inventoryProductEntry = inventoryProductEntry;
    }


    public InventoryProductEntryModel toModel(){
        InventoryProductEntryModel inventoryProductEntryModel = new InventoryProductEntryModel();
        inventoryProductEntryModel.setId(String.valueOf(inventoryProductEntry.getId()));
        inventoryProductEntryModel.setAction(inventoryProductEntry.getInventoryProductAction().toString());
        inventoryProductEntryModel.setQuantity(inventoryProductEntry.getQuantity().toString());
        inventoryProductEntryModel.setCreateTime(inventoryProductEntry.getCreateTime().toString());
        inventoryProductEntryModel.setEnteredBy(inventoryProductEntry.getEnteredBy());
        return inventoryProductEntryModel;
    }
}
