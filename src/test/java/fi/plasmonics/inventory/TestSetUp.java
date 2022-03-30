package fi.plasmonics.inventory;

import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;
import fi.plasmonics.inventory.entity.InventoryProductActions;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.entity.ProductType;
import fi.plasmonics.inventory.entity.UnitOfMeasure;
import fi.plasmonics.inventory.services.ContainerService;
import fi.plasmonics.inventory.services.ProductService;

public abstract class TestSetUp {
    public static final String TEST_USER = "TEST_USER";
    @Mock
    protected ContainerService containerService;

    @Mock
    protected ProductService productService;

    static final String TEST_DESC = "Product for Inventory Testing";

    static final Long RAW_PRODUCT_ID = 99L;
    static final String RAW_PRODUCT_NAME = "RAW_PRODUCT";

    static final Long FINISHED_PRODUCT_ID = 100L;
    static final String FINISHED_PRODUCT_NAME = "FINISHED_PRODUCT";

    static final Long INVENTORY_PRODUCT_ID = 101L;
    static final String INVENTORY_PRODUCT_NAME = "INVENTORY_PRODUCT";

    static final String CONTAINER_BARCODE = "9999999999";
    static final String FAKE_CONTAINER_BARCODE = "9999999998";


    static final Long INVENTORY_PRODUCT_INCOMING_ENTRY_ID = 102L;
    static final Long INVENTORY_PRODUCT_OUTGOING_ENTRY_ID = 103L;


    static final Long FAKE_CONTAINER_ID = 999L;
    static final Long DELETE_CONTAINER_ID = 998L;
    static final Long NEW_CONTAINER_ID = 104L;
    static final Long OPEN_CONTAINER_ID = 105L;
    static final Long READY_CONTAINER_ID = 106L;
    static final Long DISPATCHED_CONTAINER_ID = 107L;


    protected Container createNewContainer(){
        Container container = new Container();
        container.setId(NEW_CONTAINER_ID);
        container.setContainerState(ContainerState.NEW);
        container.setProduct(createRawProduct());
        container.setCreateTime(Timestamp.from(Instant.now()));
        container.setVolume(new BigDecimal(10));
        return container;

    }

    protected Container createOpenContainer(){
        Container container = new Container();
        container.setId(OPEN_CONTAINER_ID);
        container.setContainerState(ContainerState.OPEN);
        container.setProduct(createRawProduct());
        container.setCreateTime(Timestamp.from(Instant.now()));
        container.setVolume(new BigDecimal(10));
        container.setOpenTime(Timestamp.from(Instant.now()));
        return container;

    }


    protected Container createReadyContainer(){
        Container container = new Container();
        container.setId(READY_CONTAINER_ID);
        container.setContainerState(ContainerState.READY_TO_DISPATCH);
        container.setProduct(createFinishedProduct());
        container.setCreateTime(Timestamp.from(Instant.now()));
        container.setVolume(new BigDecimal(10));
        container.setOpenTime(Timestamp.from(Instant.now()));
        container.setReadyToDispatchTime(Timestamp.from(Instant.now()));
        container.setReadyToDispatchBy(TEST_USER);
        container.setBarcode(CONTAINER_BARCODE);
        return container;

    }

    protected Container createDispatchedContainer(){
        Container container = new Container();
        container.setId(DISPATCHED_CONTAINER_ID);
        container.setContainerState(ContainerState.DISPATCHED);
        container.setProduct(createFinishedProduct());
        container.setCreateTime(Timestamp.from(Instant.now()));
        container.setVolume(new BigDecimal(10));
        container.setOpenTime(Timestamp.from(Instant.now()));
        container.setReadyToDispatchTime(Timestamp.from(Instant.now()));
        container.setReadyToDispatchBy(TEST_USER);
        container.setBarcode(CONTAINER_BARCODE);
        container.setDispatchedBy(TEST_USER);
        container.setDispatchedTime(Timestamp.from(Instant.now()));
        return container;

    }




    protected Product createRawProduct() {
        Product product = new Product();
        product.setId(RAW_PRODUCT_ID);
        product.setName(RAW_PRODUCT_NAME);
        product.setProductType(ProductType.RAW);
        product.setUnitOfMeasure(UnitOfMeasure.UNIT);
        product.setDescription(TEST_DESC);
        product.setCreateTime(Timestamp.from(Instant.now()));
        return product;
    }

    protected Product createFinishedProduct() {
        Product product = new Product();
        product.setId(FINISHED_PRODUCT_ID);
        product.setName(FINISHED_PRODUCT_NAME);
        product.setProductType(ProductType.FINISHED);
        product.setUnitOfMeasure(UnitOfMeasure.UNIT);
        product.setDescription(TEST_DESC);
        return product;
    }


    protected Product createInventoryProduct() {
        Product product = new Product();
        product.setId(INVENTORY_PRODUCT_ID);
        product.setName(INVENTORY_PRODUCT_NAME);
        product.setProductType(ProductType.RAW);
        product.setUnitOfMeasure(UnitOfMeasure.UNIT);
        product.setDescription(TEST_DESC);
        return product;
    }


    protected InventoryProductEntry createIncomingInventoryProductEntry(){
        InventoryProductEntry inventoryProductEntry = new InventoryProductEntry();
        inventoryProductEntry.setProduct(createInventoryProduct());
        inventoryProductEntry.setInventoryProductAction(InventoryProductActions.INCOMING);
        inventoryProductEntry.setCreateTime(Timestamp.from(Instant.now()));
        inventoryProductEntry.setQuantity(new BigDecimal(10));
        inventoryProductEntry.setEnteredBy(TEST_USER);
        inventoryProductEntry.setId(INVENTORY_PRODUCT_INCOMING_ENTRY_ID);
        return inventoryProductEntry;
    }

    protected InventoryProductEntry createOutGoingInventoryProductEntry(){
        InventoryProductEntry inventoryProductEntry = new InventoryProductEntry();
        inventoryProductEntry.setProduct(createInventoryProduct());
        inventoryProductEntry.setInventoryProductAction(InventoryProductActions.OUTGOING);
        inventoryProductEntry.setCreateTime(Timestamp.from(Instant.now()));
        inventoryProductEntry.setQuantity(new BigDecimal(5));
        inventoryProductEntry.setEnteredBy(TEST_USER);
        inventoryProductEntry.setId(INVENTORY_PRODUCT_OUTGOING_ENTRY_ID);
        return inventoryProductEntry;
    }
}
