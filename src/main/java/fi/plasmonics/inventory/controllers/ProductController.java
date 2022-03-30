package fi.plasmonics.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import fi.plasmonics.inventory.dto.InventoryProductDto;
import fi.plasmonics.inventory.dto.ProductDto;
import fi.plasmonics.inventory.entity.InventoryProductActions;
import fi.plasmonics.inventory.entity.InventoryProductEntry;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.entity.ProductType;
import fi.plasmonics.inventory.entity.UnitOfMeasure;
import fi.plasmonics.inventory.model.request.product.CreateInventoryProductEntry;
import fi.plasmonics.inventory.model.request.product.CreateProduct;
import fi.plasmonics.inventory.model.request.product.UpdateProduct;
import fi.plasmonics.inventory.model.response.MessageType;
import fi.plasmonics.inventory.model.response.product.DeleteProductResponse;
import fi.plasmonics.inventory.model.response.product.GetInventoryProductEntryResponse;
import fi.plasmonics.inventory.model.response.product.InventoryProductEntryActionResponse;
import fi.plasmonics.inventory.model.response.product.InventoryProductEntryModel;
import fi.plasmonics.inventory.model.response.product.ProductModel;
import fi.plasmonics.inventory.services.ContainerService;
import fi.plasmonics.inventory.services.InventoryProductEntryService;
import fi.plasmonics.inventory.services.ProductService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Autowired
    private InventoryProductEntryService inventoryProductEntryService;

    @Autowired
    private ContainerService containerService;



    @GetMapping("/products")
    public List<ProductModel> getProducts(){
        return  productService.getProducts().stream().map(product -> new ProductDto(product).toModel()).collect(Collectors.toList());
    }


    @GetMapping("/inventoryProductEntries/{productId}")
    public GetInventoryProductEntryResponse getInventoryEntries(@PathVariable String productId){
        GetInventoryProductEntryResponse getInventoryProductEntryResponse = new GetInventoryProductEntryResponse();
        Product product = productService.getProductById(Long.parseLong(productId));
        List<InventoryProductEntryModel> inventoryProductEntryModelList =   product.getInventoryProductEntries().stream().map(inventoryProductEntry -> new InventoryProductDto(inventoryProductEntry).toModel()).collect(Collectors.toList());
        getInventoryProductEntryResponse.setInventoryProductEntryItemList(inventoryProductEntryModelList);
        return getInventoryProductEntryResponse;
    }


    @PutMapping("/products")
    public Product updateProduct(@RequestBody UpdateProduct updateProduct){
        Product product = productService.getProductById(Long.parseLong(updateProduct.getId()));
        product.setName(updateProduct.getName());
        product.setProductType(Enum.valueOf(ProductType.class,updateProduct.getProductType()));
        product.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class,updateProduct.getUnitOfMeasure()));
        product.setDescription(updateProduct.getDescription());
        return productService.save(product);
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody CreateProduct createProduct){
        Product product = new Product();
        product.setName(createProduct.getName());
        product.setProductType(Enum.valueOf(ProductType.class,createProduct.getProductType()));
        product.setUnitOfMeasure(Enum.valueOf(UnitOfMeasure.class,createProduct.getUnitOfMeasure()));
        product.setDescription(createProduct.getDescription());
        product.setCreateTime(Timestamp.from(Instant.now()));
        return productService.save(product);
    }


    @PostMapping("/inventoryProductEntry")
    public InventoryProductEntryActionResponse saveInventoryProductEntry(@RequestBody CreateInventoryProductEntry createInventoryProductEntry){
        InventoryProductEntry inventoryProductEntry = new InventoryProductEntry();
        Product product = productService.getProductById(Long.parseLong(createInventoryProductEntry.getProductId()));
        ProductModel productModel = new ProductDto(product).toModel();
        BigDecimal availableQuantity = new BigDecimal(productModel.getAvailableQuantity());
        BigDecimal outGoingQuantity = new BigDecimal(createInventoryProductEntry.getQuantity());
        InventoryProductActions inventoryProductActions = Enum.valueOf(InventoryProductActions.class,createInventoryProductEntry.getAction());

        if(inventoryProductActions.equals(InventoryProductActions.INCOMING) || availableQuantity.compareTo(outGoingQuantity) >= 0){
            inventoryProductEntry.setProduct(product);
            inventoryProductEntry.setInventoryProductAction(inventoryProductActions);
            inventoryProductEntry.setEnteredBy(createInventoryProductEntry.getEnteredBy());
            inventoryProductEntry.setCreateTime(Timestamp.from(Instant.now()));
            inventoryProductEntry.setQuantity(new BigDecimal(createInventoryProductEntry.getQuantity()));
            inventoryProductEntryService.save(inventoryProductEntry);
            return  new InventoryProductEntryActionResponse(MessageType.SUCCESS,"Inventory updated");
        }else{
            return new InventoryProductEntryActionResponse(MessageType.FAILURE,"Not enough units available, Please check available units");
        }

    }



    @DeleteMapping("/products/{id}")
    public DeleteProductResponse deleteProduct(@PathVariable String id){

       Long productId = Long.parseLong(id);
       if(containerService.isContainerWithProduct(productId)){
         return   new DeleteProductResponse(MessageType.FAILURE, "Product is contained in one or more containers");
       }
       productService.delete(productId);
       return  new DeleteProductResponse(MessageType.SUCCESS, "Product deleted successfully");
    }


    @GetMapping("/products/{id}")
    public ProductModel getProductById(@PathVariable String id){
        Long productId = Long.parseLong(id);
        Product product =  productService.getProductById(productId);
        return  new ProductDto(product).toModel();
    }



}
