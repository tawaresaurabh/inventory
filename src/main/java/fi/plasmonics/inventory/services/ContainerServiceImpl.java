package fi.plasmonics.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;
import fi.plasmonics.inventory.entity.Product;
import fi.plasmonics.inventory.repo.ContainerRepository;


@Service("containerService")
public class ContainerServiceImpl implements ContainerService{

    @Autowired
    private ContainerRepository containerRepository;


    @Override
    public List<Container> getContainers() {
        return containerRepository.findAll();
    }

    @Override
    public Container save(Container container) {
        return containerRepository.save(container);
    }

    @Override
    public List<Container> saveAll(List<Container> containerList) {
        return containerRepository.saveAll(containerList);
    }
    @Override
    public List<Container> getContainerByState(ContainerState containerState) {
        return containerRepository.findByContainerState(containerState);

    }
    @Override
    public Container findByBarcode(String barcode) {
        return containerRepository.findByBarcode(barcode);

    }
    @Override
    public boolean isContainerWithProduct(Long productId){
        return getContainers().stream().anyMatch(container -> container.getProduct().getId().equals(productId));

    }
    @Override
    public boolean isContainerWithBarcode(String barcode){
        return getContainers().stream().anyMatch(container -> container.getBarcode() != null && container.getBarcode().equals(barcode));

    }

    @Override
    public Container findById(Long id) {
        Optional<Container> containerOptional = containerRepository.findById(id);
        return containerOptional.orElse(null);
    }

    @Override
    public void deleteContainer(Long id) {
        containerRepository.deleteById(id);
    }
    @Override
    public void deleteContainers(List<Long> containerIds) {
        containerIds.forEach(containerId -> containerRepository.deleteById(containerId));
    }


}
