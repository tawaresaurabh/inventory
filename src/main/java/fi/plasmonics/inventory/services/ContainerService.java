package fi.plasmonics.inventory.services;

import org.springframework.stereotype.Component;

import java.util.List;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;

@Component
public interface ContainerService {

    List<Container> getContainers();
    Container save(Container container);
    List<Container> saveAll(List<Container> containerList);
    List<Container> getContainerByState(ContainerState containerState);
    Container findByBarcode(String barcode);
    boolean isContainerWithProduct(Long productId);
    boolean isContainerWithBarcode(String barcode);
    Container findById(Long id);
    void deleteContainer(Long id);
    void deleteContainers(List<Long> containerIds);
}
