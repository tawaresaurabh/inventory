package fi.plasmonics.inventory.services;

import org.springframework.stereotype.Component;

import java.util.List;

import fi.plasmonics.inventory.entity.Product;

@Component
public interface ProductService {

     List<Product> getProducts();
     Product save(Product product);
     void delete(Long id);
     Product getProductById(Long id);
}
