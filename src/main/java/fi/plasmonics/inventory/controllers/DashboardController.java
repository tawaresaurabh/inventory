package fi.plasmonics.inventory.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import fi.plasmonics.inventory.entity.Container;
import fi.plasmonics.inventory.entity.ContainerState;
import fi.plasmonics.inventory.model.response.dashboard.DashboardResponse;
import fi.plasmonics.inventory.services.ContainerService;
import fi.plasmonics.inventory.services.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DashboardController {





    @Autowired
    private ProductService productService;

    @Autowired
    private ContainerService containerService;

    @GetMapping("/dashboard/containers")
    public DashboardResponse getContainers(){
        List<Container> containerList = containerService.getContainers();
        Long newContainerCount = containerList.stream().filter(container -> container.getContainerState().equals(ContainerState.NEW)).count();
        Long readyToDispatchContainerCount = containerList.stream().filter(container -> container.getContainerState().equals(ContainerState.READY_TO_DISPATCH)).count();
        Long dispatchedContainerCount = containerList.stream().filter(container -> container.getContainerState().equals(ContainerState.DISPATCHED)).count();
        Long openContainerCount = containerList.stream().filter(container -> container.getContainerState().equals(ContainerState.OPEN)).count();
        return new DashboardResponse(newContainerCount,readyToDispatchContainerCount,dispatchedContainerCount,openContainerCount);
    }





}
