package fi.plasmonics.inventory.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import fi.plasmonics.inventory.entity.InventoryProductActions;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.model.response.product.ProductModel;

public class ProductDto implements Serializable {
    private Product product;


    public ProductDto(Product product) {
        this.product = product;
    }



    public ProductModel toModel(){
        ProductModel productModel = new ProductModel();
        productModel.setId(String.valueOf(product.getId()));
        productModel.setProductType(String.valueOf(product.getProductType()));
        productModel.setUnitOfMeasure(String.valueOf(product.getUnitOfMeasure()));
        productModel.setDescription(product.getDescription());
        productModel.setName(product.getName());
        if(product.getCreateTime() != null){
            productModel.setCreateTime(product.getCreateTime().toString());
        }

        BigDecimal qty = BigDecimal.ZERO;
        for(InventoryProductEntry inventoryProductEntry: product.getInventoryProductEntries()){
            if(inventoryProductEntry.getInventoryProductAction().equals(InventoryProductActions.INCOMING)){
               qty =  qty.add(inventoryProductEntry.getQuantity());
            }else{
                qty =  qty.subtract(inventoryProductEntry.getQuantity());
            }
        }
        productModel.setAvailableQuantity(qty.toString());

        return productModel;
    }
}
