package fi.plasmonics.inventory;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.entity.ItemOrder;
import fi.plasmonics.inventory.entity.ItemOrderType;
import fi.plasmonics.inventory.entity.UnitOfMeasure;
import fi.plasmonics.inventory.services.ItemService;
@ExtendWith(MockitoExtension.class)
public class TestSetUp {
    public static final String TEST_USER = "TEST_USER";


    @Mock
    protected ItemService itemService;

    static final String TEST_DESC = "Product for Inventory Testing";

    static final Long ITEM_ID = 99L;
    static final String ITEM_NAME = "TEST_ITEM";
    static final String ITEM_CREATOR = "TEST_ITEM_CREATOR";

    static final Long ITEM_ORDER_ID_INCOMING = 102L;
    static final Long ITEM_ORDER_ID_OUTGOING = 103L;



    protected Item createItem() {
        Item item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setUnitOfMeasure(UnitOfMeasure.UNIT);
        item.setDescription(TEST_DESC);
        item.setCreateTime(Timestamp.from(Instant.now()));
        item.setCreatedBy(ITEM_CREATOR);
        return item;
    }




    protected ItemOrder createIncomingItemOrder() {
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItem(createItem());
        itemOrder.setItemOrderType(ItemOrderType.INCOMING);
        itemOrder.setCreateTime(Timestamp.from(Instant.now()));
        itemOrder.setQuantity(new BigDecimal(10));
        itemOrder.setCreatedBy(TEST_USER);
        itemOrder.setId(ITEM_ORDER_ID_INCOMING);
        return itemOrder;
    }

    protected ItemOrder createOutgoingItemOrder() {
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setItem(createItem());
        itemOrder.setItemOrderType(ItemOrderType.OUTGOING);
        itemOrder.setCreateTime(Timestamp.from(Instant.now()));
        itemOrder.setQuantity(new BigDecimal(5));
        itemOrder.setCreatedBy(TEST_USER);
        itemOrder.setId(ITEM_ORDER_ID_OUTGOING);
        return itemOrder;
    }

}
