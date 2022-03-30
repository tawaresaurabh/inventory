package fi.plasmonics.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fi.plasmonics.inventory.controllers.ProductController;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.entity.ProductType;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.model.response.product.DeleteProductResponse;
import fi.plasmonics.inventory.model.response.product.GetInventoryProductEntryResponse;
import fi.plasmonics.inventory.model.response.product.ProductModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductTests extends TestSetUp {

    @InjectMocks
    ProductController productController;

    private Product rawProduct;
    private Product inventoryProduct;
    private Set<InventoryProductEntry> inventoryProductEntrySet;

    List<Product> productList;


    @BeforeEach
    void setup() {
        rawProduct = createRawProduct();
        inventoryProduct = createInventoryProduct();
        inventoryProductEntrySet = new HashSet<>();
        inventoryProductEntrySet.add(createIncomingInventoryProductEntry());
        inventoryProductEntrySet.add(createOutGoingInventoryProductEntry());
        inventoryProduct.setInventoryProductEntries(inventoryProductEntrySet);
        productList = new ArrayList<>();
        productList.add(createRawProduct());
        productList.add(createFinishedProduct());

    }


    @Test
    void checkGetProductById(){
        when(productService.getProductById(RAW_PRODUCT_ID)).thenReturn(rawProduct);
        ProductModel productModel = productController.getProductById(String.valueOf(RAW_PRODUCT_ID));
        assertEquals(ProductType.RAW, Enum.valueOf(ProductType.class,productModel.getProductType()));

    }

    @Test
    void checkGetProducts(){
        when(productService.getProducts()).thenReturn(productList);
        List<ProductModel> productModelList = productController.getProducts();
        assertEquals(2, productModelList.size());
    }

    @Test
    void checkGetInventoryProductEntry(){
        when(productService.getProductById(INVENTORY_PRODUCT_ID)).thenReturn(inventoryProduct);
        GetInventoryProductEntryResponse getInventoryProductEntryResponse = productController.getInventoryEntries(String.valueOf(INVENTORY_PRODUCT_ID));
        assertEquals(2, getInventoryProductEntryResponse.getInventoryProductEntryItemList().size());
    }


    @Test
    void checkDeleteProduct_fail(){
        when(containerService.isContainerWithProduct(RAW_PRODUCT_ID)).thenReturn(true);
        DeleteProductResponse deleteProductResponse = productController.deleteProduct(String.valueOf(RAW_PRODUCT_ID));
        assertEquals(MessageType.FAILURE, deleteProductResponse.getMessageType());
    }


    @Test
    void checkDeleteProduct_success(){
        when(containerService.isContainerWithProduct(RAW_PRODUCT_ID)).thenReturn(false);
        doNothing().when(productService).delete(RAW_PRODUCT_ID);
        DeleteProductResponse deleteProductResponse = productController.deleteProduct(String.valueOf(RAW_PRODUCT_ID));
        assertEquals(MessageType.SUCCESS, deleteProductResponse.getMessageType());
    }



}
