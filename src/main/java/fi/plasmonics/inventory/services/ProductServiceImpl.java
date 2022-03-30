package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.repo.ProductRepository;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    @Override
    public Product save(Product product){
        return productRepository.save(product);
    }
    @Override
    public void delete(Long id){
         productRepository.deleteById(id);
    }
    @Override
    public Product getProductById(Long id){
        return  productRepository.findById(id).get();
    }



}
