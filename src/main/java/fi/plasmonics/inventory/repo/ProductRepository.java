package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.plasmonics.inventory.entity.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product , Long > {
}
