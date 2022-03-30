package fi.plasmonics.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;

@Repository("containerRepository")
public interface ContainerRepository  extends JpaRepository<Container, Long> {

    List<Container> findByContainerState(ContainerState containerState);
    Container findByBarcode(String barcode);

}
