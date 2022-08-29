package fi.plasmonics.inventory.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(info = @Info(title = "Inventory Api for plasmonics", version = "1", description = "Track and "
    + "Manage Inventory"), tags = {
    @Tag(name = OpenApiConfig.TAG_ITEMS, description = "CRUD operations for Items.\n"
        + "Add, edit or delete Items from the inventory.\n"),
    @Tag(name = OpenApiConfig.TAG_ITEM_ORDER, description = "Manage Incoming and outgoing item quantities\n"),
})
public class OpenApiConfig {

    private OpenApiConfig() {
    }


    public static final String TAG_ITEMS = "Items";
    public static final String TAG_ITEM_ORDER = "Item Order";
}
